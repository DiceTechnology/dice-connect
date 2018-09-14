import React from 'react';
import { Platform, View } from 'react-native';
import { AirPlayButton } from 'dice-connect/src/airPlay';
import { CastButton } from 'dice-connect/src/GoogleCast';

import styles from './styles';

interface IChromecastState {
  available: boolean
}

interface ITopBarProps {
  airPlayAvailable: boolean;
  chromecastState?: IChromecastState;
}

export class TopBar extends React.PureComponent<ITopBarProps, {}> {
  render() {
    const {
      airPlayAvailable,
      // chromecastState,
    } = this.props;

    const showAirplay = Platform.OS === 'ios' && airPlayAvailable;

    return (
      <View style={ styles.container }>
        { showAirplay ? <AirPlayButton /> : <CastButton /> }
      </View>
    );
  }
}
