import * as React from 'react';
import { requireNativeComponent, ViewStyle, StyleProp, } from 'react-native';

interface Props {
  style?: StyleProp<ViewStyle>;
}

const GoogleCastButton: React.ComponentClass<Props> = requireNativeComponent('GoogleCastButton');

export class CastButton extends React.Component<Props> {
  render() {
    const { style } = this.props
    return <GoogleCastButton style={[{ height: 44, width: 44 }, style]} />;
  }
}

