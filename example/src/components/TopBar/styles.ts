import { StyleSheet } from 'react-native';
import * as Constants from '../../constants';

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'red',
    position: 'absolute',
    top: Constants.TOP_BAR_PADDING * 2,
    flex: 1,
    flexDirection: 'row',
    zIndex: 100,
    marginLeft: Constants.PADDING,
    marginRight: Constants.PADDING,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default styles;
