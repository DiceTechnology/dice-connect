
# dice-connect

## Getting started

`$ npm install dice-connect --save`

### Mostly automatic installation

`$ react-native link dice-connect`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `dice-connect` and add `RNDiceConnect.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNDiceConnect.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.dicetechnology.dcchromecast.DCGoogleCastPackage;` to the imports at the top of the file
  - Add `new DCGoogleCastPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':dice-connect'
  	project(':dice-connect').projectDir = new File(rootProject.projectDir, 	'../node_modules/dice-connect/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':dice-connect')
  	```
4. Add the string resource containing the id of your cast receiver to resources
      ```
      <string name="cast_id" translatable="false">YOUR_CAST_RECEIVER_ID</string>
      ```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNDiceConnect.sln` in `node_modules/dice-connect/windows/RNDiceConnect.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Dice.Connect.RNDiceConnect;` to the usings at the top of the file
  - Add `new RNDiceConnectPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNDiceConnect from 'dice-connect';

// TODO: What to do with the module?
RNDiceConnect;
```
  
