import {
  DeviceEventEmitter,
  EventSubscriptionVendor,
  NativeEventEmitter,
  NativeModules,
  Platform,
  EventEmitter,
} from 'react-native';

import {
  DeviceScannerState,
  StreamType,
  ContentType,
  Metadata,
} from './models';

const { DCGoogleCast } = NativeModules;

interface ChromecastEventEmitter extends EventSubscriptionVendor {
  getState: (
    callback: (error: null, result: DeviceScannerState) => void,
  ) => void;
  requestQueuedItems: () => void;
  start: (
    url: string,
    streamType: StreamType,
    contentType: ContentType,
    metadata?: Metadata,
  ) => void;
  stop: () => void;
  disconnect: () => void;
  play: () => void;
  pause: () => void;
  seek: (toMilliseconds: number, relative: boolean) => void;
}

const DCGoogleCastTyped = DCGoogleCast as ChromecastEventEmitter;

export const emitter: EventEmitter = Platform.select({
  ios: new NativeEventEmitter(DCGoogleCastTyped),
  android: DeviceEventEmitter,
});

export const getState = () =>
  new Promise<DeviceScannerState>((resolve, reject) => {
    DCGoogleCastTyped.getState((error, result) => resolve(result));
  });

export const requestQueuedItems = DCGoogleCastTyped.requestQueuedItems;

export const start = (
  url: string,
  streamType: StreamType,
  contentType: ContentType,
  metadata?: Metadata,
) => DCGoogleCastTyped.start(url, streamType, contentType, metadata);

export const stop = DCGoogleCastTyped.stop;

export const disconnect = DCGoogleCastTyped.disconnect;

export const play = DCGoogleCastTyped.play;

export const pause = DCGoogleCastTyped.pause;

export const seek = (toMilliseconds: number, relative: boolean = true) =>
  DCGoogleCastTyped.seek(toMilliseconds, relative);
