import {
  requireNativeComponent,
  NativeModules,
  NativeEventEmitter,
  EventSubscriptionVendor,
  EmitterSubscription,
} from 'react-native';

const { DCAirPlay } = NativeModules;

export interface IDCAirplay extends EventSubscriptionVendor {
  startScan: () => void;
  disconnect: () => void;
}
export enum AirPlayEvents {
  'DEVICE_AVAILABLE' = 'deviceAvailable',
  'DEVICE_CONNECTED' = 'deviceConnected',
}

export interface IAirPlayListenerPayload {
  airplay: boolean;
}

export type AirPlayListener = (deviceStatus: IAirPlayListenerPayload) => any;

export interface IAirPlayEmitter {
  addListener(
    eventName: AirPlayEvents,
    listener: AirPlayListener,
    context: any
  ): EmitterSubscription;
}

export const AirPlay = DCAirPlay as IDCAirplay;

export const AirPlayButton = requireNativeComponent('DCAirPlay');

export const AirPlayEmitter: IAirPlayEmitter = new NativeEventEmitter(DCAirPlay);
