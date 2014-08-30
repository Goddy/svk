/**
 * Created by u0090265 on 5/11/14.
 */

var svk = svk || {};
svk.utils = {
    postForm : function(url, data, func) {
        $.ajax({
            type: "POST",
            url: url,
            data:  data,
            success: func,
            error: function(xhr, status, error) {
                var err = eval("(" + xhr.responseText + ")");
                console.log(err.Message);
            },
            dataType: "text"
        });
    },
    setKeyupAndBlur: function (element, func) {
        element.on({
            keyup: function () {
                func();
            },
            blur:  function () {
                func();
            }
        });
    }
}
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
                showSuccessBox(resultDiv, data);
            },
            error: function(err) {
                console.log(err);
                showErrorBox(resultDiv, msg[1]);
            }
        });
    }

    function showSuccessBox(div, txt) {
        $(div).removeAttr('class');
        div.html(txt);
        $(div).addClass('alert alert-success');
        div.show();
    }

    function showErrorBox(div, txt) {
        div.removeAttr('class');
        div.html(txt);
        div.addClass('alert alert-danger');
        div.show();
    }

    return {
        changePassword: function(form, msg, resultDiv) {
            resultDiv.hide();
            var newPassword = getFormElement(form, "newPassword");
            var confirmPassword = getFormElement(form, "confirmPassword");

            if (!checkPassword(newPassword, confirmPassword)) {
                showErrorBox(resultDiv, msg[0]);
            }
            else {
                sendPassword(form, msg, resultDiv);
            }
        }
    }
})(jQuery);