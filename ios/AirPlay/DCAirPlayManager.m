//
//  DCAirPlayManager.m
//  RNDiceConnect
//
//  Created by Nadia Dillon on 14/09/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "DCAirPlayManager.h"
#import "DCAirPlayManager.h"
#import <React/RCTBridge.h>
#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>
#import <MediaPlayer/MediaPlayer.h>

@implementation DCAirPlayManager

RCT_EXPORT_MODULE();

@synthesize bridge = _bridge;

- (UIView *) view {
    MPVolumeView *volumeView = [[MPVolumeView alloc] init];
    volumeView.showsVolumeSlider = false;
    
    return volumeView;
}

@end
