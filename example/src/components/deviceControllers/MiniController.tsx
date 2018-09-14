import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { IVideo } from '../../App';
import { TouchableIcon } from '../Icon/TouchableIcon';

interface IProps {
  deviceType: string;
  isPlaying: boolean;
  onExpand(): void;
  video: IVideo;
}

export class MiniController extends React.Component<IProps> {
  render() {
    const {
      isPlaying,
      deviceType,
      video,
      // onExpand
    } = this.props;

    const { title } = video;
    const controlIconName = isPlaying ? 'pause' : 'play';

    return (
      <View style={ styles.container }>

        <View style={ styles.progressBar }/>

        <View
          style={ styles.banner }
          // onPress={onExpand}
        >

          <View style={ styles.textContainer }>
            { title ? <Text style={ styles.title }>{ title }</Text> : null }
            <Text style={ styles.connectedText }>Connected to { deviceType }</Text>
          </View>

          <View style={ [ styles.iconContainer, styles.playPauseIcon ] }>
            <TouchableIcon name={ controlIconName } onPress={ () => console.log('play/pause') }/>
          </View>
          { /*<View style={[styles.iconContainer, styles.expandIcon]}>*/ }
          { /*<TouchableIcon name={'arrowUp'} onPress={onExpand}/> */ }
          { /*</View>*/ }

        </View>

      </View>
    )
  }
}

const styles = StyleSheet.create({
  container: {
    width: '100%',
  },
  progressBar: {
    height: 3, // REMOVE HEIGHT
    width: '100%',
    backgroundColor: 'blue',
  },
  banner: {
    alignItems: 'center',
    width: '100%',
    backgroundColor: '#181818',
    justifyContent: 'center',
    flexDirection: 'row',
    padding: 5,
  },
  textContainer: {
    alignItems: 'center',
  },
  title: {
    color: '#696969',
    fontSize: 14,
    padding: 5,
  },
  connectedText: {
    color: '#a8a8a8',
    padding: 5,
  },
  iconContainer: {
    alignItems: 'center',
    justifyContent: 'center',
    position: 'absolute',
    height: 50,
    width: 50,
  },
  playPauseIcon: {
    right: 20,
  },
  expandIcon: {
    left: 20,
  }
});
