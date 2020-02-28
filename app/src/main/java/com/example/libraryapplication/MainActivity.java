package com.example.libraryapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.libraryapplication.component.Book;
import com.example.libraryapplication.controlers.VisualSearch;
import com.example.libraryapplication.utility.VisualSearchAdapter;
import com.example.libraryapplication.utility.VolleyResponseListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.BaseStream;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends Fragment {


    private static final int CAMERA_REQUEST_CODE = 0;

    private TextureView camera_layout;
    private Button get_titles;
    private Button reset;
    private CameraManager cameraManager;
    private int cameraFacing;
    private TextureView.SurfaceTextureListener surfaceTextureListener;
    private CameraDevice.StateCallback stateCallback;
    private String cameraId;
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;
    private BaseStream cameraCaptureSession;
    private CameraDevice cameraDevice;
    private CaptureRequest captureRequest;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size previewSize;
    private ImageView image;
    private ArrayList<Book> books;
    private RecyclerView titles;
    private VisualSearchAdapter booksListAdapter;
    private ConstraintLayout titleRect;
    private Bitmap image_view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main,null);
        camera_layout = view.findViewById(R.id.camera_layout);
        get_titles = view.findViewById(R.id.get_list);
        reset = view.findViewById(R.id.reset);
        image = view.findViewById(R.id.preview);
        titles = view.findViewById(R.id.books_list);
        titleRect = view.findViewById(R.id.rect);


        titles.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        titles.setLayoutManager(layoutManager);
        titles.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        surfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                setUpCamera();
                openCamera();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        };
        stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice cameraDevice) {
                MainActivity.this.cameraDevice = cameraDevice;
                createPreviewSession();
            }

            @Override
            public void onDisconnected(CameraDevice cameraDevice) {
                cameraDevice.close();
                cameraDevice = null;
            }

            @Override
            public void onError(CameraDevice cameraDevice, int error) {
                cameraDevice.close();
                cameraDevice = null;
            }
        };

        get_titles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                image_view = camera_layout.getBitmap();
                if (image_view!= null) {
                    VisualSearch visualSearch = new VisualSearch(getContext());
                    visualSearch.recognizeText(image_view, listener);
                    lock(image_view);
                }
                else Toast.makeText(getContext(),"no photo was detected!", Toast.LENGTH_SHORT).show();

            }
        });



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlock(books);
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

        cameraManager = getSystemService(getContext(), CameraManager.class);

        cameraFacing = CameraCharacteristics.LENS_FACING_BACK;

    }



    VolleyResponseListener listener = new VolleyResponseListener() {
        @Override
        public void onError(String message) {
            // do something...
        }

        @Override
        public void onResponse(Object response) {
            books = (ArrayList<Book>) response;
            displayTitles(books);
        }
    };


    private void displayTitles(ArrayList<Book> books) {
        booksListAdapter = new VisualSearchAdapter(books,getContext(), image_view);
        titles.setAdapter(booksListAdapter);
    }

    private void unlock(ArrayList<Book> books) {

        if(books!= null) {
            books.clear();
            booksListAdapter.notifyDataSetChanged();
            titles.removeAllViewsInLayout();
            }
            camera_layout.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);

            //TODO clear the ConstraintLayout from title rectangle if available

    }


    private void lock(Bitmap bitmap) {

        camera_layout.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
        image.setImageBitmap(bitmap);


    }

    private void setUpCamera() {
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics =
                        cameraManager.getCameraCharacteristics(cameraId);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
                        cameraFacing) {
                    StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(
                            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//                    previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
                    previewSize =chooseOptimalSize(streamConfigurationMap
                            .getOutputSizes(SurfaceTexture.class), 300, 380);
                    this.cameraId = cameraId;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(cameraId, stateCallback, backgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Size chooseOptimalSize(Size[] outputSizes, int width, int height) {
        double preferredRatio = height / (double) width;

        Size currentOptimalSize = outputSizes[0];
        double currentOptimalRatio = currentOptimalSize.getWidth() / (double) currentOptimalSize.getHeight();
        for (Size currentSize : outputSizes) {
            double currentRatio = currentSize.getWidth() / (double) currentSize.getHeight();
            if (Math.abs(preferredRatio - currentRatio) <
                    Math.abs(preferredRatio - currentOptimalRatio)) {
                currentOptimalSize = currentSize;
                currentOptimalRatio = currentRatio;
            }
        }
        return currentOptimalSize;
    }


    private void openBackgroundThread() {
        backgroundThread = new HandlerThread("camera_background_thread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    @Override
    public void onResume() {
        super.onResume();

        openBackgroundThread();
        if (camera_layout.isAvailable()) {
            setUpCamera();
            openCamera();
        } else {
            camera_layout.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    private void createPreviewSession() {
        try {
            SurfaceTexture surfaceTexture = camera_layout.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            if (cameraDevice == null) {
                                return;
                            }

                            try {
                                captureRequest = captureRequestBuilder.build();
                                cameraCaptureSession = cameraCaptureSession;
                                cameraCaptureSession.setRepeatingRequest(captureRequest,
                                        null, backgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {

                        }
                    }, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }}

    @Override
    public void onStop() {
        super.onStop();
        closeCamera();
        closeBackgroundThread();
    }

    private void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }

        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void closeBackgroundThread() {
        if (backgroundHandler != null) {
            backgroundThread.quitSafely();
            backgroundThread = null;
            backgroundHandler = null;
        }
    }

    }
