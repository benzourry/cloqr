// Intercepting HTTP calls with AngularJS.
Attend.config(function($provide, $httpProvider, $locationProvider) {

    // Intercept http calls.
    $provide.factory('AccessInterceptor', AccessInterceptor);

    AccessInterceptor.$inject=['$templateCache','$q'];

    function AccessInterceptor($templateCache, $q) {
        return {
            // On request failure
            requestError: function(rejection) {
                // CIRCUIT-BREAKER
                // Return the promise rejection.
                //  return $q.reject(rejection);
                alert("request error");
            },
            // On response success
            response: function(response) {

                var modifiedTemplates = {};

                var HAS_FLAGS_EXP = /data-(keep|omit)/;
                var IS_HTML_PAGE = /\.jsp$|\.html|\.jsp\?/i;

                var url = response.config.url;
                var responseData = response.data;

                // console.log("URL:"+responseData);

                if (!modifiedTemplates[url] && IS_HTML_PAGE.test(url) &&
                    HAS_FLAGS_EXP.test(responseData)) {

                    // Create a DOM fragment from the response HTML.
                    var template = $('<div>').append(responseData);

                    // Find and parse the keep/omit attributes in the view.
                    template.find('[data-keep],[data-omit]').each(function() {
                        var element = $(this),
                            data = element.data(),
                            keep = data.keep,
                            features = keep || data.omit || '';

                        // Check if the user has all of the specified features.
                        var hasFeature = _.all(features.split(','), function(feature) {
                            //console.log(feature);
                            //console.log(feature+':'+(activeProfile.features.indexOf(feature) > -1?'true':'false'));
                            //console.log(JSON.stringify(activeProfile.features));
                            return (activeProfile.features.indexOf(feature) > -1);
                            //     return activeProfile.features[feature];
                        });

                        if (features.length && ((keep && !hasFeature) || (!keep && hasFeature))) {
                            element.remove();
                        }
                    });

                    // Set the modified template.
                    response.data = template.html();

                    // Replace the template in the template cache, and mark the
                    // template as modified.
                    $templateCache.put(url, response.data);

                    modifiedTemplates[url] = true;
                    //console.log('cache:' + url + ': notModified & isJspHtml & hasFlag');
                }

                // Return the response or promise.
                return response || $q.when(response);
            },
            // On response failture
            responseError: function(rejection) {

                if(rejection.status == 401){
                    var hash = window.location.hash;
                    window.location ='login'+hash;
                    console.log(hash);
                }

                // console.log(rejection); // Contains the data about the error.
                // Return the promise rejection.
                return $q.reject(rejection);
            }
        };
    }

    // Add the interceptor to the $httpProvider.
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    $httpProvider.interceptors.push('AccessInterceptor');

});
