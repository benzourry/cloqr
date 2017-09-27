/**
 * Created by MohdRazif on 9/26/2017.
 */
angular.module('app')
    .component("sessionList", {
        templateUrl: "_apps/session/session_list.html",
        controller: SessionList
    });

SessionList.$inject = ["$http", "$routeParams"];

function SessionList($http, $routeParams) {

    var vm = this;

    vm.getEventList = getEventList;
    vm.deleteSession = deleteSession;

    vm.$onInit = function(){
        getEventList();
    }

    function getEventList(){
        $http.get("api/event/list").then(
            function (res) {
                vm.sessionList = res.data.content;
            }
        );
    }

    function deleteSession(id){
            if (confirm("Are you sure you want to delete?")) {

                $http.delete("api/event/" + id).then(
                    function (res) {
                        vm.alertMessage = "Session successfully removed";
                        $http.get("api/event/list").success(
                            function (res) {
                                vm.sessionList = res.data.content;
                            }
                        );
                    }
                );
        }
    }

}