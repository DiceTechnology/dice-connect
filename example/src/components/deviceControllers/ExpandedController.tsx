import * as React from 'react';
import { View } from 'react-native';
import { IVideo } from '../../App';

interface IProps {
  video: IVideo;
  deviceType: string;
  onExpand: () => void;
}

export class ExpandedController extends React.Component<IProps> {
  render() {
    return (
      <View />
    )
  }
}