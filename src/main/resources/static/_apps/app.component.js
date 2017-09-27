/**
 * Created by MohdRazif on 10/14/2016.
 */
angular.module("app")
    .component("appRoot",{
        templateUrl: '_apps/app.component.html',
        controller: AppComponent
    });

AppComponent.$inject = ["userService"];
function AppComponent(userService){
    var $ctrl = this;
    // $ctrl.toggle = toggle;
    $ctrl.logout = logout;

    $ctrl.user = {};


    $ctrl.$onInit = function(){
        userService.get().then(function(user){
            $ctrl.user = user;
        });
    }

    // function toggle(){
    //     $ctrl.active = !$ctrl.active;
    // }
    //
    function logout(){
        userService.logout();
    }



}
