package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class BtScanner {

    private static UUID[] MY_UUIDS = new UUID[]{new UUID("b9b323accbbd11e7abc4cec278b6b50b", false)};


    public static void main(String[] args) {
        try {
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();

            final CountDownLatch deviceScanDone = new CountDownLatch(1);
            final List<RemoteDevice> devices = new ArrayList<>();

            agent.startInquiry(DiscoveryAgent.GIAC, new DiscoveryListener() {
                @Override
                public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
                    try {

                        System.out.println("deviceDiscovered: " +
                                remoteDevice.getFriendlyName(false) + " - " +
                                remoteDevice.getBluetoothAddress());

                        devices.add(remoteDevice);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void servicesDiscovered(int i, ServiceRecord[] serviceRecords) {
                    System.out.println("Services discovered");
                }

                @Override
                public void serviceSearchCompleted(int i, int i1) {

                }

                @Override
                public void inquiryCompleted(int i) {
                    deviceScanDone.countDown();
                }
            });

            deviceScanDone.await();


            for (RemoteDevice device : devices) {
                final CountDownLatch serviceScanDone = new CountDownLatch(1);
                System.out.println(device);
                agent.searchServices(null, MY_UUIDS, device, new DiscoveryListener() {

                    @Override
                    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {

                    }

                    @Override
                    public void servicesDiscovered(int i, ServiceRecord[] serviceRecords) {
                        System.out.println("Service discovered");
                        for (ServiceRecord record: serviceRecords) {
                            System.out.println(record.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
                        }
                    }

                    @Override
                    public void serviceSearchCompleted(int i, int i1) {
                        serviceScanDone.countDown();
                    }

                    @Override
                    public void inquiryCompleted(int i) {

                    }
                });
                serviceScanDone.await();
            }

        } catch (BluetoothStateException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
