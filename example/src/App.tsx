import React from 'react';
import { View, Text } from 'react-native';
import VideoPlayer from './components/VideoPlayer';

// import { Controllers } from './components';

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
      manifestUrl: 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
      title: 'Big Buck Bunny',
      duration: 36000,
      thumbnailUrl: '',
    },
    deviceType: 'Chromecast',
    isConnectedToDevice: true,
    isPlaying: true,
  };

  render() {
    const {video: {manifestUrl}} = this.state;
    return (
      <View style={ {flex: 1, justifyContent: 'space-between'} }>
        <Text style={{ color: 'black'}}>Hey</Text>
        <VideoPlayer source={ {uri: manifestUrl} }/>
        {/*<Controllers { ...this.state }/>*/}
      </View>
    );
  }
}
