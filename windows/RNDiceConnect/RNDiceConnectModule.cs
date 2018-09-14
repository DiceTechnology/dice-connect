using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Dice.Connect.RNDiceConnect
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNDiceConnectModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNDiceConnectModule"/>.
        /// </summary>
        internal RNDiceConnectModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNDiceConnect";
            }
        }
    }
}
