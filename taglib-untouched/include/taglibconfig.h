
#ifndef TAGLIB_TAGLIBCONFIG_H
#define TAGLIB_TAGLIBCONFIG_H

#	if defined( __MINGW32__ )
		// Linux compilers don't have symbol import/export directives.
#   	define _TagLibExport
#   else
#   	if defined( TAGLIB_EXPORTS )
#       	define _TagLibExport __declspec( dllexport )
#   	else
#       	define _TagLibExport __declspec( dllimport )
#   	endif
#	endif


#endif