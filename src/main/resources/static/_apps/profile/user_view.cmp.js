/**
 * Created by MohdRazif on 9/26/2017.
 */
angular.module('app')
    .component("userView", {
        templateUrl: "_apps/profile/user_view.html",
        controller: UserView
    });

UserView.$inject = ["$http", "$routeParams", "userService"];

function UserView($http, $routeParams, userService) {
    // var vm = this;

    var vm = this;

    vm.user = {};
    vm.getAccount = getAccount;
    vm.getLogs = getLogs;
    vm.getEventList = getEventList;

    vm.$onInit = function(){
        userService.get().then(function(user){
            vm.user = user;
            getLogs();
            getEventList();
        });
    }

    function getAccount(){
        $http.get("api/account/" + vm.user.username + "").then(
            function (res) {
                vm.data = res.data;
            }
        );

    }

    function getLogs(){
        $http.get("api/account/" + vm.user.username + "/logs").then(
            function (res) {
                vm.logList = res.data.content;
            }
        );

    }

    function getEventList(){
        $http.get("api/event/list").then(
            function (res) {
                vm.sessionList = res.data.content;
            }
        );

    }

}