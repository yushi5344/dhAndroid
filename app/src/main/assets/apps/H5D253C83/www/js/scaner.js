document.addEventListener( "plusready",  function()
{
    var _BARCODE = 'Scaner',
		B = window.plus.bridge;
    var Scaner =
    {
    	ScanCode : function (Argus, successCallback, errorCallback )
		{
			var success = typeof successCallback !== 'function' ? null : function(args)
			{
				successCallback(args);
			},
			fail = typeof errorCallback !== 'function' ? null : function(code)
			{
				errorCallback(code);
			};
			callbackID = B.callbackId(success, fail);

			return B.exec(_BARCODE, "ScanCode", [callbackID, Argus]);
		}
    };
    window.plus.Scaner = Scaner;
}, true );