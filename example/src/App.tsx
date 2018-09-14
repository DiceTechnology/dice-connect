import React from 'react';
import { View } from 'react-native';
import VideoPlayer from './components/VideoPlayer';

import { Controllers } from './components';

export interface IVideo {
  manifestUrl?: string;
  title?: string;
  duration?: number;
  thumbnailUrl?: string;
}

interface IState {
  deviceType: string;
  isConnectedToDevice: boolean;
  isPlaying: boolean;
  video: IVideo,
}

export default class App extends React.Component<{}, IState> {
  state = {
    video: {
      manifestUrl: '',
      title: 'Big Buck Bunny',
      duration: 36000,
      thumbnailUrl: '',
    },
    deviceType: 'Chromecast',
    isConnectedToDevice: true,
    isPlaying: true,
  };

  render() {
    return (
      <View style={{ flex: 1 }}>
        <VideoPlayer source={{uri: 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4'}}/>
        <View style={{ flex: 1, justifyContent: 'flex-end' }}>
          <Controllers {...this.state}/>
        </View>
      </View>
    );
  }
}
