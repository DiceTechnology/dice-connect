import * as React from 'react';
import { View } from 'react-native';

import { MiniController } from './MiniController';
import { ExpandedController } from './ExpandedController';
import { IVideo } from '../../App';

interface IProps {
  deviceType: string;
  isConnectedToDevice: boolean;
  isPlaying: boolean;
  video: IVideo;
}

interface IState {
  expanded: boolean;
}

export class Controllers extends React.Component<IProps, IState> {
  state = { expanded: false }

  setController = () => {
    this.setState(prevState => ({expanded: !prevState.expanded}))
  }

  render() {
    const { expanded } = this.state;
    const { isConnectedToDevice } = this.props;

    if (!isConnectedToDevice) return null;
    return (
      <View> 
        {expanded ? (
          <ExpandedController {...this.props} onExpand={this.setController}/>
        ) : (
          <MiniController {...this.props} onExpand={this.setController} />
        )}
      </View>
    )
  }
}