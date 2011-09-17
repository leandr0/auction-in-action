/**
 * 
 */

function showDivHideDiv(showDiv,hideDiv){
	jQuery(hideDiv).hide();
	jQuery(showDiv).fadeIn(1500);
}

function hideDiv(idDiv){
	jQuery(idDiv).hide();
}

function showDiv(idDiv){
	jQuery(showDiv).fadeIn(1500);
}