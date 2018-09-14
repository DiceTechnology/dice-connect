import * as React from 'react';
import Svg, { Path, Polygon } from 'react-native-svg';
import { TouchableOpacity } from 'react-native';

interface IIconProps {
  width: number;
  height: number;
  fill: string;
  viewBox: string;
}

const ArrowUp = ({ width, height, fill, viewBox }: IIconProps) => (
  <Svg width={width} height={height} viewBox={viewBox} >
    <Path 
      fill={fill}
      // tslint:disable-next-line
      d="M0 15c0 0.128 0.049 0.256 0.146 0.354 0.195 0.195 0.512 0.195 0.707 0l8.646-8.646 8.646 8.646c0.195 0.195 0.512 0.195 0.707 0s0.195-0.512 0-0.707l-9-9c-0.195-0.195-0.512-0.195-0.707 0l-9 9c-0.098 0.098-0.146 0.226-0.146 0.354z" 
    />
  </Svg>
);

const ArrowDown = ({ width, height, fill, viewBox }: IIconProps) => (
  <Svg width={width} height={height} viewBox={viewBox} >
    <Polygon
      fill={fill} 
      // tslint:disable-next-line
      points="121.300 34.600, 115.500 34.600, 64.500 85.700, 13.400 34.600, 7.600 34.600, 7.600 40.400, 61.500 94.300, 64.400 95.500, 67.300 94.300, 121.200 40.400, 121.300 34.600" />
  </Svg>
);

const Play = ({ width, height, fill, viewBox }: IIconProps) => (
  <Svg width={width} height={height} viewBox={viewBox}>
    <Path 
      fill={fill}
      // tslint:disable-next-line
      d="M1,.14A.69.69,0,0,0,.27.07.76.76,0,0,0,0,.76V23.24a.76.76,0,0,0,.27.69A.69.69,0,0,0,1,23.86L17.56,12.67a.72.72,0,0,0,0-1.33Z"  
      />
  </Svg>
);

const Pause = ({ width, height, fill, viewBox }: IIconProps) => (
  <Svg width={width} height={height} viewBox={viewBox} key={++width}>
    <Path 
      fill={fill} 
      // tslint:disable-next-line
      d="M0,23.39V.61A.54.54,0,0,1,.61,0H5.44Q6,0,6,.61V23.39Q6,24,5.44,24H.61A.54.54,0,0,1,0,23.39Zm12,0V.61A.54.54,0,0,1,12.61,0h4.83Q18,0,18,.61V23.39q0,.61-.56.61H12.61A.54.54,0,0,1,12,23.39Z" 
    />
  </Svg>
);

const icons = {
  arrowUp: ArrowUp,
  arrowDown: ArrowDown,
  play: Play,
  pause: Pause,
}

interface ITouchableIconProps { 
  name: 'arrowUp' | 'arrowDown' | 'play' | 'pause', 
  onPress: () => void
}

export const TouchableIcon = ({ name, onPress }: ITouchableIconProps) => {
  const iconConfig = {
    width: 20,
    height: 20,
    fill: '#a8a8a8',
    viewBox: '0 0 20 20',
  };
  const Icon = icons[name];
  return (
    <TouchableOpacity onPress={onPress}>
      <Icon {...iconConfig}/>
    </TouchableOpacity>
  )
}
