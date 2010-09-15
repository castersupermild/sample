$(function() {

	var form = '<form></form>';

	$('a[name=delete]').click(function() {
		if (!confirm('削除してよろしいですか？')) {
			return false;
		}
		return true;
	})

	$('td.cell_update').live('dblclick', function() {
		var target = $(this);
		var editCell = $(document).find('td.editing');
		if (editCell.length) {
			editCell.html(editCell.find('input[name=cache]').val()).removeClass('editing');
		}

		target.addClass('editing');
		var value = target.text();
		var name = target.hasClass('cell_name_update') ? 'name': 'email';
		var textBox = '<input type="text" name="' + name + '" value="' + value + '" style="width: 100%;" />';
		var cache = '<input type="hidden" name="cache" value="' + value + '" />';
		target.html($(form).append(textBox).append(cache));
		target.find('input').focus();
	});

	$('form').live('submit', function() {
		var target = $(this);
		var tr = $(this).parent().parent();
		var isName = target.parent().hasClass('cell_name_update');
		var value = target.find('input').val();
		var data = {
			key: tr.find('input[name=key]').val()
		};

		if (isName) {
			data.name = value;
			data.email = tr.find('td.cell_email_update').text();
		} else {
			data.email = value;
			data.name = tr.find('td.cell_name_update').text();
		}

		$.ajax({
			type: 'POST',
			url: '../crud/update',
			data: data,
			dataType: 'json',
			success: function(res){
			  	if (res.msg) {
			  		var msges = res.msg;
			  		var msg = '';
			  		for (var i = 0, len = msges.length; i < len; i++) {
			  			msg += msges[i];
			  		}
			  		$('#errMsg').html(msg);
			  		return;
			  	}
			  	tr.find('td.cell_name_update').html(res.person.name).removeClass('editing');
			  	tr.find('td.cell_email_update').html(res.person.email).removeClass('editing');
			}
		});
		return false;
	});
});