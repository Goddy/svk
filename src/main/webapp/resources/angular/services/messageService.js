'use strict';
app.factory('messageService', function($http, $cookies, $timeout) {
    var locale_nl = {
        'alert.vote.success' : 'Je stem werd geregistreerd',
        'alert.vote.fail' : 'Het is niet gelukt om een stem uit te brengen',
        'alert.poll.reset.success' : 'Poll succesvol gereset',
        'alert.poll.reset.fail' : 'Er heeft zich een fout voorgedaan. De poll werd niet gereset',
        'alert.poll.refresh.success' : 'Poll succesvol vernieuwd',
        'alert.poll.refresh.fail' : 'Er heeft zich een fout voorgedaan. De poll werd niet vernieuwd'
    };

    var locale_en = {
        'alert.vote.success' : 'Your vote was registered',
        'alert.vote.fail' : 'An error has occurred. The vote did not succeed',
        'alert.poll.reset.success' : 'Poll succesfully reset',
        'alert.poll.reset.fail' : 'An error has occurred. The poll has not been reset',
        'alert.poll.refresh.success' : 'Poll succesfully refreshed',
        'alert.poll.refresh.fail' : 'An error has occurred. The poll has not been refreshed'
    };

    function getLocale() {
        var lang = $cookies.get('clientlanguage');
        return lang === 'en' ? locale_en : locale_nl;
    }

    function getMessage(messageCode) {
        return getLocale()[messageCode];
    }

    return {
        getMessage: function(messageCode) {
            return getMessage(messageCode);
        },

        showMessage: function(msgF, messageCode) {
            msgF(getMessage(messageCode));
            $timeout(function () { msgF("") }, 3000);
        }
    }
});
