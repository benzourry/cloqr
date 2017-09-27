/**
 * Created by MohdRazif on 10/17/2016.
 */

angular.module("app")
    .factory("userService", userService);
userService.$inject = ["$http"];
function userService ($http){
    //var vm = this;
    //vm.user = {};
    var promise;
    return {
        get: getUser,
        logout: logout,
        getToken: getToken
    }

    function getUser(){
        if (!window.localStorage.getItem("identity")){
            window.location.href = OAUTH.AUTH_URI+"?client_id=" + OAUTH.CLIENT_ID + "&response_type=token&redirect_uri="+OAUTH.CALLBACK;

        }else{
            var access_token = JSON.parse(window.localStorage.getItem("identity")).oauth.access_token;
            $http.defaults.headers.common['Authorization'] = 'Bearer '+ access_token;
            if (!promise){
                promise = $http.get(OAUTH.USER_URI+"?access_token="+access_token).then(function(res){
                    return res.data.principal;
                }, function(res){
                    // window.location.href = OAUTH.AUTH_URI+"?client_id=" + OAUTH.CLIENT_ID + "&response_type=token&redirect_uri="+OAUTH.CALLBACK;
                });
            }
        }
        return promise;
    }

    function getToken(){
        return JSON.parse(window.localStorage.getItem("identity")).oauth.access_token;
    }

    function logout(){
        window.localStorage.removeItem("identity");
        window.location.href = "/admin/start";
    }

}
