import React from 'react';
import { View } from 'react-native';
import Video, { OnLoadData, VideoProperties } from 'react-native-video';

import styles from './styles';

export class VideoPlayer extends React.Component<VideoProperties> {

  constructor(props: VideoProperties) {
    super(props);
    this.onLoad = this.onLoad.bind(this);
    this.onProgress = this.onProgress.bind(this);
    this.onBuffer = this.onBuffer.bind(this);
  }

  state = {
    rate: 1,
    volume: 1,
    muted: false,
    resizeMode: 'contain',
    duration: 0.0,
    currentTime: 0.0,
    controls: true,
    paused: false,
    ignoreSilentSwitch: 'obey',
    isBuffering: false,
  };

  onLoad(data: OnLoadData) {
    this.setState({duration: data.duration});
  }

  onProgress(data: OnLoadData) {
    this.setState({currentTime: data.currentTime});
  }

  onBuffer({isBuffering}: { isBuffering: boolean }) {
    this.setState({isBuffering});
  }

  render() {
    const {source: {uri}} = this.props;

    return (
      <View style={ styles.container }>
        <Video
          style={ styles.fullScreen }
          source={ {uri} }
          rate={ this.state.rate }
          paused={ this.state.paused }
          volume={ this.state.volume }
          muted={ this.state.muted }
          resizeMode={ this.state.resizeMode }
          onLoad={ this.onLoad }
          repeat={ true }
          controls={ this.state.controls }
        />
      </View>
    );
  }
}
