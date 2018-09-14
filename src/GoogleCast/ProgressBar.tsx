import React from 'react';
import { requireNativeComponent, ViewStyle, StyleProp } from 'react-native';

interface IProps {
  progressColorHex?: string;
  progressMax?: number;
  progressCurrent?: number;
  progressTimeHidden?: boolean;
  progressThumbHidden?: boolean;
  style?: StyleProp<ViewStyle>;
}

const GoogleCastProgressBar: React.ComponentClass<IProps> = requireNativeComponent(
  'GoogleCastProgressView',
);

export class ProgressBar extends React.Component<IProps> {
  render() {
    const { style, ...rest } = this.props;
    return <GoogleCastProgressBar
      progressTimeHidden={false}
      progressThumbHidden={false}
      progressColorHex={'#00ff00'}
      style={[{ height: 44, width: '100%' }, style]}
      {...rest}
    />;
  }
}
