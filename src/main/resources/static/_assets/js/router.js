var Attend = angular.module('attend', [
    'ngRoute', 'ngSanitize', 'ngMessages'
]).run(function () {
    //FastClick.attach(document.body);
});

Attend.config(['$routeProvider', '$controllerProvider', '$compileProvider', '$locationProvider','$httpProvider',
    function ($routeProvider, $controllerProvider, $compileProvider, $locationProvider,$httpProvider, $routeParams) {
        Attend.registerCtrl = $controllerProvider.register;
        Attend.registerDirective = $compileProvider.directive;
        $routeProvider.
            /* Profile */
            when('/', {templateUrl: '_apps/profile/view.html'}).
            //when('/profile', {templateUrl: '_apps/profile/view.html'}).
            when('/session/add', {templateUrl: '_apps/session_add.html'}).
            when('/session/list', {templateUrl: '_apps/session_list.html'}).
            when('/session/:id', {templateUrl: '_apps/session_view.html'}).
            when('/session/edit/:id', {templateUrl: '_apps/session_add.html'}).
            when('/user/:username', {templateUrl: '_apps/user_view.html'}).
            when('/profile', {templateUrl: '_apps/user_view.html'}).
            //when('/admin/calendar', {templateUrl: '_apps/admin/calendar.html'}).
            //when('/admin/activity', {templateUrl: '_apps/admin/activity.html'}).
            /* Catch-all redirective*/
            otherwise({
                redirectTo: '/404'
            });
        $locationProvider
            .html5Mode(false)
            .hashPrefix('!');

        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    }]);

//Attend.directive('fallbackSrc', function () {
//    var fallbackSrc = {
//        link: function postLink(scope, iElement, iAttrs) {
//            iElement.bind('error', function () {
//                angular.element(this).attr("src", iAttrs.fallbackSrc);
//            });
//        }
//    }
//    return fallbackSrc;
//});
