/**
 * Created by u0090265 on 5/11/14.
 */

var svk = svk || {};

svk.updatePassword = (function($){
    function checkPassword(newPwd, repeatPwd) {
        return newPwd.val() === repeatPwd.val();
    }

    function getFormElement(form, name) {
        return form.find(":input[name='" + name + "']");
    }

    function sendPassword(form, msg, resultDiv) {
        $.ajax({
            type: "POST",
            url: 'changePassword.json',
            data: form.serialize(),
            cache: false,
            dataType: "text",
            success: function(data) {
                resultDiv.html(data);
                resultDiv.show();
            },
            error: function(err) {
                console.log(err);
                resultDiv.html(msg[1]);
                resultDiv.show();
            }
        });
    }

    return {
        changePassword: function(form, msg, resultDiv) {
            resultDiv.hide();
            var newPassword = getFormElement(form, "newPassword");
            var confirmPassword = getFormElement(form, "confirmPassword");

            if (!checkPassword(newPassword, confirmPassword)) {
                resultDiv.html(msg[0]);
                resultDiv.show();
            }
            else {
                sendPassword(form, msg, resultDiv);
            }
        }
    }
})(jQuery);