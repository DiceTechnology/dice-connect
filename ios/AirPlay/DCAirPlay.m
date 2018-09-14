//
//  DCAirPlay.m
//  RNDiceConnect
//
//  Created by Dice Technology on 14/09/2018.
//  Copyright © 2018 Dice Technology. All rights reserved.
//

#import "DCAirPlay.h"
#import "DCAirPlayManager.h"
#import <AVFoundation/AVFoundation.h>
#import <AudioToolbox/AudioToolbox.h>
#import <MediaPlayer/MediaPlayer.h>

@implementation DCAirPlay
@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(startScan)
{
    AVAudioSessionRouteDescription* currentRoute = [[AVAudioSession sharedInstance] currentRoute];
    BOOL isAvailable = NO;
    NSUInteger routeNum = [[currentRoute outputs] count];
    if(routeNum > 0) {
        isAvailable = YES;
        BOOL isConnected = YES;
        for (AVAudioSessionPortDescription * output in currentRoute.outputs) {
            if([output.portType isEqualToString:AVAudioSessionPortAirPlay]) {
                [self sendEventWithName:@"deviceConnected" body:@{@"airPlay": @(isConnected)}];
            }
        }
        
        [[NSNotificationCenter defaultCenter]
         addObserver:self
         selector: @selector(airplayChanged:)
         name:MPVolumeViewWirelessRouteActiveDidChangeNotification
         object:nil];
    }
    [self sendEventWithName:@"deviceAvailable" body:@{@"airPlay": @(isAvailable)}];
}

RCT_EXPORT_METHOD(disconnect)
{
    printf("disconnect Airplay");
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [self sendEventWithName:@"deviceAvailable" body:@{@"airPlay": @(NO) }];
}


- (void)airplayChanged:(NSNotification *)sender
{
    AVAudioSessionRouteDescription* currentRoute = [[AVAudioSession sharedInstance] currentRoute];
    
    BOOL isAirPlayPlaying = NO;
    for (AVAudioSessionPortDescription* output in currentRoute.outputs) {
        if([output.portType isEqualToString:AVAudioSessionPortAirPlay]) {
            isAirPlayPlaying = YES;
            break;
        }
    }
    [self sendEventWithName:@"deviceConnected" body:@{@"airPlay": @(isAirPlayPlaying)}];
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"deviceAvailable", @"deviceConnected"];
}


@end
