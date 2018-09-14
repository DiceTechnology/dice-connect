export const DeviceScannerEvent = {
  DEVICE_SCANNER_STATE: 'DCE_DEVICES_STATE',
};

export enum DeviceScannerState {
  noDevicesAvailable = 'NO_DEVICES_AVAILABLE',
  notConnected = 'NOT_CONNECTED',
  connected = 'CONNECTED',
  videoStarting = 'VIDEO_STARTING',
  videoStarted = 'VIDEO_STARTED',
  videoEnded = 'VIDEO_ENDED',
  videoFailed = 'VIDEO_FAILED',
  videoPlaying = 'VIDEO_PLAYING',
  videoPaused = 'VIDEO_PAUSED',
  videoBuffering = 'VIDEO_BUFFERING',
}

export const SessionManagerEvent = {
  SESSION_MANAGER_SEEK_STATE: 'SESSION_MANAGER_SEEK_STATE',
};

export enum SeekState {
  seeking = 'SEEKING',
  completed = 'COMPLETED',
  failed = 'FAILED',
}

export enum StreamType {
  vod = 'VOD',
  live = 'LIVE',
}

export function streamTypeFromString(value: string): StreamType | null {
  switch (value) {
    case 'VOD':
    case 'VOD_VIDEO':
      return StreamType.vod;
    case 'LIVE':
    case 'LIVE_EVENT':
      return StreamType.live;
    default:
      return null;
  }
}

export enum ContentType {
  vod = 'application/vnd.apple.mpegurl',
  live = 'application/vnd.apple.mpegurl',
}

export function contentTypeFromString(value: string): ContentType | null {
  switch (value) {
    case 'VOD':
    case 'VOD_VIDEO':
      return ContentType.vod;
    case 'LIVE':
    case 'LIVE_EVENT':
      return ContentType.live;
    default:
      return null;
  }
}

export interface Metadata {
  title: string;
  imageUrl: string;
  duration: number;
  startPosition: number;
  eventInfoSerialized?: string;
  chromecastSessionSerialized?: string;
}
