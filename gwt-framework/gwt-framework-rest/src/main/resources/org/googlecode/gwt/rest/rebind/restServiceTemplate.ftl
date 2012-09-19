        private final static Logger LOG = Logger.getLogger(${serviceQualifiedClassName}.class.getName());

        private String url;

        public ${serviceClassName}() {
            this.url = "${applicationPath}";
        }

        <#list methods as method>
        interface MyRestServiceJSONBeanFactory${method.uniqueName} extends AutoBeanFactory {
            <#if method.hasCallbackInnerType >
                AutoBean<${method.callbackInnerType}> creator();
            </#if>
        }

        <#if method.produces?? >
        private java.util.Set<String> produces_${method.uniqueName} = new java.util.HashSet<String>();
        {
            String[] producesArray = new String[]{
            <#list method.produces as produce>
                "${produce}",
            </#list>
            };

            java.util.Collections.addAll(produces_${method.uniqueName}, producesArray);
        }
        </#if>

        @Override
        ${method.declaration} {

            final MyRestServiceJSONBeanFactory${method.uniqueName} myFactory${method.uniqueName} = GWT.create(MyRestServiceJSONBeanFactory${method.uniqueName}.class);
            final ${method.callbackType} finalCallback = ${method.callbackName};

            String contextPath = GWT.getModuleBaseURL().replace("/"+GWT.getModuleName()+"/", "");
            String methodURL = contextPath+url+"${method.path}";

            StringBuilder inputBuilder = new StringBuilder();
            StringBuilder queryParam = new StringBuilder("?");

            <#list method.parameters as parameter>
                <#if parameter.isInputParameter >
            AutoBean<${parameter.type}> ${parameter.nome}AutoBean = AutoBeanUtils.getAutoBean(${parameter.nome});
            String inputJSON = AutoBeanCodex.encode(${parameter.nome}AutoBean).getPayload();
            inputBuilder.append(inputJSON);
            GWT.log(inputJSON);
                <#elseif parameter.isPathParameter>
            methodURL = methodURL.replace("${parameter.pathParam}", String.valueOf(${parameter.nome}));
                <#elseif parameter.isQueryParameter>
            if( ${parameter.nome}!=null ) {
                queryParam.append("${parameter.queryParam}");
                queryParam.append('=');
                queryParam.append(${parameter.nome});
                queryParam.append('&');
            }
                </#if>
            </#list>

            queryParam.setLength(queryParam.length()-1);
            methodURL = methodURL + queryParam.toString();

            RequestBuilder builder = new RequestBuilder(RequestBuilder.${method.httpMethod}, methodURL);

            GWT.log(builder.getHTTPMethod()+" "+builder.getUrl());

            <#if method.hasContentType>
            builder.setHeader("Content-Type", "${method.contentType}");
            </#if>

            <#list method.parameters as parameter>
                <#if parameter.isHeaderParameter>
            builder.setHeader("${parameter.headerParam}", String.valueOf(${parameter.nome}));
                </#if>
            </#list>

            builder.setCallback(new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    finalCallback.onError(0, exception);
                }

                public void onResponseReceived(Request request, Response response) {
                    <#if method.hasCallbackInnerType >

                    ${method.callbackInnerType} retVal = null;

                    String text = response.getText();
                    if( text!=null && !"".equals(text) ) {
                        LOG.fine(text);
                        <#if method.produces?? >

                        if( produces_${method.uniqueName}.contains(response.getHeader("Content-Type")) ) {
                            retVal = AutoBeanCodex.decode(myFactory${method.uniqueName}, ${method.callbackInnerType}.class, text).as();
                        }
                        <#else>
                        retVal = AutoBeanCodex.decode(myFactory${method.uniqueName}, ${method.callbackInnerType}.class, text).as();
                        </#if>
                    }

                    finalCallback.onResponseReceived(response,retVal);
                    <#else>
                    finalCallback.onResponseReceived(response);
                    </#if>

                }
            });
            builder.setRequestData(inputBuilder.toString());

            try {
                return builder.send();
            } catch (RequestException e) {
                throw new RuntimeException(e);
            }
        }
        </#list>

        @Override
        public ${serviceQualifiedClassName} withURL(String url) {
            this.url = url;
            return this;
        }

