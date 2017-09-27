/**
 * Created by MohdRazif on 10/14/2016.
 */

angular.module('app', [
    'ngRoute', 'ui.bootstrap', 'ngMessages', 'oc.lazyLoad'
])
.config(['$locationProvider',
    function($locationProvider){
        $locationProvider
            .html5Mode(false)
            .hashPrefix('!');
    }
])
.run(['$rootScope', '$uibModalStack',
    function($rootScope, $uibModalStack){
        $rootScope.$on('$locationChangeStart', function($event){
            var openedModal = $uibModalStack.getTop();
            if (openedModal){
                if (!!$event.preventDefault){
                    $event.preventDefault();
                }
                if (!!$event.stopPropagation){
                    $event.stopPropagation();
                }
                $uibModalStack.dismiss(openedModal.key);
            }
        });
}])



