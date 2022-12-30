$(document).ready(function(){
	
	$('input:checkbox').change(
    function(){
        if ($(this).is(':checked')) {
            $('#professionista').show();
            $("#formFileMultiple").prop('required',true);
            $("#professione").prop('required',true);
        }else{
			$('#professionista').hide();
			$("#formFileMultiple").prop('required',false);
			$("#professione").prop('required',false);
		}
    });
	
});