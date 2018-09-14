import * as React from 'react';
import { requireNativeComponent, ViewStyle, StyleProp } from 'react-native';

import { RealmContext } from 'DiceMobile.Contexts/RealmContext';

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
    return (
      <RealmContext.Consumer>
        {({ theme }) => (
          <GoogleCastProgressBar
            progressTimeHidden={false}
            progressThumbHidden={false}
            progressColorHex={theme.colors.primary}
            style={[{ height: 44, width: '100%' }, style]}
            {...rest}
          />
        )}
      </RealmContext.Consumer>
    );
  }
}
