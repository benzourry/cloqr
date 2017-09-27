/**
 * Created by MohdRazif on 9/26/2017.
 */
angular.module('app')
    .component("sessionView", {
        templateUrl: "_apps/session/session_view.html",
        controller: SessionAdd
    });

SessionView.$inject = ["$http", "$routeParams"];

function SessionView($http, $routeParams) {
    var vm = this;

    vm.$onInit = function(){
        $http.get("api/event/"+$routeParams.id+"").then(
            function (res) {
                vm.data = res.data;
            }
        );
        $http.get("api/event/"+$routeParams.id+"/logs").then(
            function (res) {
                vm.logList = res.data.content;
            }
        );
    }
}