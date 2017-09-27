/**
 * Created by MohdRazif on 9/26/2017.
 */
angular.module('app')
.component("sessionAdd", {
    templateUrl: "_apps/session/session_add.html",
    controller: SessionAdd
});

SessionAdd.$inject = ["$http", "$routeParams"];

function SessionAdd($http, $routeParams) {


    var vm = this;
    vm.getEvent = getEvent;
    vm.saveEvent = saveEvent;

    vm.$onInit = function(){

    }

    function getEvent(id){
        $http.get("api/event/" + id + "")
            .then(
            function (res) {
                $scope.data = res.data;
            }
        );
    }

    function saveEvent(){
        $http.post("api/event/create", vm.data).then(
            function (data) {
                vm.alertMessage="Event successfully saved";
            }
        );
    }
    // if ($routeParams.id) {
    //
    // }

    // vm.submitForm = function () {
    //
    // }

}