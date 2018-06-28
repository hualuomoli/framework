<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="/doc/css/font-awesome.css" />
    <link rel="stylesheet" href="/doc/css/api.css" />
</head>

<body>
    <div class="container">
        <div class="header">
            <h3>${method}</h3>
            <span class="description">(${title})</span>
            <p>${description}</p>
        </div>
        <!-- content -->
        <div class="content">
            <!-- url -->
            <div class="data url">
                <h5>请求地址</h5>
                <table>
                    <tr>
                        <th width="35%">环境</th>
                        <th width="65%">HTTPS请求地址</th>
                    </tr>
                    <#list servers as server>
                    <tr>
                        <td>${server.name}</td>
                        <td>${server.url}</td>
                    </tr>
                    </#list>
                </table>
            </div>
            <!-- ./url -->

            <!-- 公共请求参数 -->
            <div class="data common-request">
                <h5>公共请求参数</h5>
                <table>
                    <tr>
                        <th>参数</th>
                        <th>类型</th>
                        <th>是否必填</th>
                        <th>最大长度</th>
                        <th>描述</th>
                        <th>示例值</th>
                    </tr>
                    <tr>
                        <td>partnerId</td>
                        <td>String</td>
                        <td>是</td>
                        <td>32</td>
                        <td>合作伙伴ID</td>
                        <td>2014072300007148</td>
                    </tr>
                    <tr>
                        <td>method</td>
                        <td>String</td>
                        <td>是</td>
                        <td>128</td>
                        <td>接口名称</td>
                        <td>${method}</td>
                    </tr>
                    <tr>
                        <td>timestamp</td>
                        <td>String</td>
                        <td>是</td>
                        <td>19</td>
                        <td>发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"</td>
                        <td>2014-07-24 03:07:50</td>
                    </tr>
                    <tr>
                        <td>nonceStr</td>
                        <td>String</td>
                        <td>是</td>
                        <td>32</td>
                        <td>随机字符串</td>
                        <td>2jSuRZ0jQFf3Z5Em</td>
                    </tr>
                    <tr>
                        <td>signType</td>
                        <td>String</td>
                        <td>是</td>
                        <td>10</td>
                        <td>商户生成签名字符串所使用的签名算法类型，目前支持RSA</td>
                        <td>RSA</td>
                    </tr>
                    <tr>
                        <td>encryptType</td>
                        <td>String</td>
                        <td>否</td>
                        <td>10</td>
                        <td>商户加密请求业务内容所使用的加密算法类型，目前支持AES。注：加密要在签名后执行</td>
                        <td>AES</td>
                    </tr>
                    <tr>
                        <td>sign</td>
                        <td>String</td>
                        <td>是</td>
                        <td>256</td>
                        <td>商户请求参数的签名串，<a href="/doc/sign.html">详见签名</a></td>
                        <td>详见示例</td>
                    </tr>
                    <tr>
                        <td>version</td>
                        <td>String</td>
                        <td>是</td>
                        <td>6</td>
                        <td>调用的接口版本，固定为：1.0</td>
                        <td>1.0</td>
                    </tr>
                    <tr>
                        <td>bizContent</td>
                        <td>String</td>
                        <td>是</td>
                        <td>-</td>
                        <td>请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档</td>
                        <td></td>
                    </tr>
                </table>
            </div>
            <!-- ./公共请求参数 -->
            
            <!-- 请求参数 -->
            <div class="data request"></div>
                <h5>请求参数</h5>
                <table>
                    <tr>
                        <th>参数</th>
                        <th>类型</th>
                        <th>是否必填</th>
                        <th>最大长度</th>
                        <th>描述</th>
                        <th>示例值</th>
                    </tr>
                    <#list requestParameters as requestParameter>
                    <#if requestParameter.type == 'Array' || requestParameter.type == 'Object'>
                    <#-- 显示的数据 -->
                    <tr class="level-control" data-open="#request-${requestParameter.name}-${requestParameter.level}">
                        <td>
                            <i class="fa fa-plus-square-o"></i>
                            <i class="fa fa-minus-square-o hide"></i>
                            ${requestParameter.name}
                        </td>
                        <td>${requestParameter.type!''}</td>
                        <td>${requestParameter.required?string('是','否')}</td>
                        <td>${requestParameter.maxLength!'-'}</td>
                        <td>${requestParameter.description}</td>
                        <td>${requestParameter.sample!''}</td>
                    </tr>
                    <#-- 子集数据 -->
                    <tr class="level-data hide" id="request-${requestParameter.name}-${requestParameter.level}">
                        <td colspan="6">
                            <#list requestParameter.parameters as parameter>
                            <#if parameter.type == 'Array' || parameter.type == 'Object'>
                            <#-- 显示的数据 -->
                            <ul class="level-${parameter.level} level-control" data-open="#request-${parameter.name}-${parameter.level}">
                                <li>
                                    <i class="fa fa-plus-square-o"></i>
                                    <i class="fa fa-minus-square-o hide"></i>
                                    ${parameter.name}
                                </li>
                                <li>${parameter.type}</li>
                                <li>${parameter.required?string('是','否')}</li>
                                <li>${parameter.maxLength!'-'}</li>
                                <li>${parameter.description}</li>
                                <li>${parameter.sample!''}</li>
                            </ul>
                            <#-- 子集数据 -->
                            <div class="level-data hide" id="request-${parameter.name}-${parameter.level}">
                                <#list parameter.parameters as subParameter>
                                <ul class="level-2">
                                    <li>${subParameter.name}</li>
                                    <li>${subParameter.type}</li>
                                    <li>${subParameter.required?string('是','否')}</li>
                                    <li>${subParameter.maxLength!'-'}</li>
                                    <li>${subParameter.description}</li>
                                    <li>${subParameter.sample!''}</li>
                                </ul>
                                </#list>
                            </div>
                            <#else>
                            <#-- 普通数据 -->
                            <ul class="level-${parameter.level}">
                                <li>${parameter.name}</li>
                                <li>${parameter.type}</li>
                                <li>${parameter.required?string('是','否')}</li>
                                <li>${parameter.maxLength!'-'}</li>
                                <li>${parameter.description}</li>
                                <li>${parameter.sample!''}</li>
                            </ul>
                            </#if>
                            </#list>
                        </td>
                    </tr>
                    <#else>
                    <#-- 普通数据 -->
                    <tr>
                        <td>${requestParameter.name}</td>
                        <td>${requestParameter.type!''}</td>
                        <td>${requestParameter.required?string('是','否')}</td>
                        <td>${requestParameter.maxLength!'-'}</td>
                        <td>${requestParameter.description}</td>
                        <td>${requestParameter.sample!''}</td>
                    </tr>
                    </#if>
                    </#list>
                </table>
            </div>
            <!-- ./请求参数 -->

            <!-- 公共响应参数 -->
            <div class="data common-response">
                <h5>公共响应参数：</h5>
                <table>
                    <tr>
                        <th>参数</th>
                        <th>类型</th>
                        <th>是否必填</th>
                        <th>最大长度</th>
                        <th>描述</th>
                        <th>示例值</th>
                    </tr>
                    <tr>
                        <td>code</td>
                        <td>String</td>
                        <td>是</td>
                        <td>-</td>
                        <td>网关返回码,详见文档</td>
                        <td>40004</td>
                    </tr>
                    <tr>
                        <td>message</td>
                        <td>String</td>
                        <td>是</td>
                        <td>-</td>
                        <td>网关返回码描述,详见文档</td>
                        <td>Business Failed</td>
                    </tr>
                    <tr>
                        <td>subCode</td>
                        <td>String</td>
                        <td>否</td>
                        <td>-</td>
                        <td>业务处理编码,详见文档</td>
                        <td>ACQ.TRADE_HAS_SUCCESS</td>
                    </tr>
                    <tr>
                        <td>subMessage</td>
                        <td>String</td>
                        <td>否</td>
                        <td>-</td>
                        <td>业务处理信息,详见文档</td>
                        <td>交易已被支付</td>
                    </tr>
                    <tr>
                        <td>subErrorCode</td>
                        <td>String</td>
                        <td>否</td>
                        <td>-</td>
                        <td>业务处理错误编码,详见文档</td>
                        <td>96523689</td>
                    </tr>
                    <tr>
                        <td>nonceStr</td>
                        <td>String</td>
                        <td>是</td>
                        <td>-</td>
                        <td>与请求随机字符串相同</td>
                        <td>2jSuRZ0jQFf3Z5Em</td>
                    </tr>
                    <tr>
                        <td>result</td>
                        <td>String</td>
                        <td>是</td>
                        <td>-</td>
                        <td>返回业务数据</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>sign</td>
                        <td>String</td>
                        <td>是</td>
                        <td>-</td>
                        <td>签名,<a href="/doc/sign.html">详见文档</a></td>
                        <td>DZXh8eeTuAHoYE3w1J+POiPhfDxOYBfUNn1lkeT/V7P4zJdyojWEa6IZs6Hz0yDW5Cp/viufUb5I0/V5WENS3OYR8zRedqo6D+fUTdLHdc+EFyCkiQhBxIzgngPdPdfp1PIS7BdhhzrsZHbRqb7o4k3Dxc+AAnFauu4V6Zdwczo=</td>
                    </tr>
                </table>
            </div>
            <!-- ./公共响应参数 -->

            <!-- 响应参数 -->
            <div class="data response">
                <h5>响应参数</h5>
                <table>
                    <tr>
                        <th>参数</th>
                        <th>类型</th>
                        <th>是否必填</th>
                        <th>最大长度</th>
                        <th>描述</th>
                        <th>示例值</th>
                    </tr>
                    <#list responseParameters as responseParameter>
                    <#if responseParameter.type == 'Array' || responseParameter.type == 'Object'>
                    <#-- 显示的数据 -->
                    <tr class="level-control" data-open="#response-${responseParameter.name}-${responseParameter.level}">
                        <td>
                            <i class="fa fa-plus-square-o"></i>
                            <i class="fa fa-minus-square-o hide"></i>
                            ${responseParameter.name}
                        </td>
                        <td>${responseParameter.type!''}</td>
                        <td>${responseParameter.required?string('是','否')}</td>
                        <td>${responseParameter.maxLength!'-'}</td>
                        <td>${responseParameter.description}</td>
                        <td>${responseParameter.sample!''}</td>
                    </tr>
                    <#-- 子集数据 -->
                    <tr class="level-data hide" id="response-${responseParameter.name}-${responseParameter.level}">
                        <td colspan="6">
                            <#list responseParameter.parameters as parameter>
                            <#if parameter.type == 'Array' || parameter.type == 'Object'>
                            <#-- 显示的数据 -->
                            <ul class="level-${parameter.level} level-control" data-open="#response-${parameter.name}-${parameter.level}">
                                <li>
                                    <i class="fa fa-plus-square-o"></i>
                                    <i class="fa fa-minus-square-o hide"></i>
                                    ${parameter.name}
                                </li>
                                <li>${parameter.type}</li>
                                <li>${parameter.required?string('是','否')}</li>
                                <li>${parameter.maxLength!'-'}</li>
                                <li>${parameter.description}</li>
                                <li>${parameter.sample!''}</li>
                            </ul>
                            <#-- 子集数据 -->
                            <div class="level-data hide" id="response-${parameter.name}-${parameter.level}">
                                <#list parameter.parameters as subParameter>
                                <ul class="level-2">
                                    <li>${subParameter.name}</li>
                                    <li>${subParameter.type}</li>
                                    <li>${subParameter.required?string('是','否')}</li>
                                    <li>${subParameter.maxLength!'-'}</li>
                                    <li>${subParameter.description}</li>
                                    <li>${subParameter.sample!''}</li>
                                </ul>
                                </#list>
                            </div>
                            <#else>
                            <#-- 普通数据 -->
                            <ul class="level-${parameter.level}">
                                <li>${parameter.name}</li>
                                <li>${parameter.type}</li>
                                <li>${parameter.required?string('是','否')}</li>
                                <li>${parameter.maxLength!'-'}</li>
                                <li>${parameter.description}</li>
                                <li>${parameter.sample!''}</li>
                            </ul>
                            </#if>
                            </#list>
                        </td>
                    </tr>
                    <#else>
                    <#-- 普通数据 -->
                    <tr>
                        <td>${responseParameter.name}</td>
                        <td>${responseParameter.type!''}</td>
                        <td>${responseParameter.required?string('是','否')}</td>
                        <td>${responseParameter.maxLength!'-'}</td>
                        <td>${responseParameter.description}</td>
                        <td>${responseParameter.sample!''}</td>
                    </tr>
                    </#if>
                    </#list>
                </table>
            </div>
            <!-- ./响应参数 -->

            <!-- 业务错误码 -->
            <div class="data error">
                <h5>业务错误码</h5>
                <table>
                    <tr>
                        <th>错误码</th>
                        <th>错误描述</th>
                        <th>解决方案</th>
                    </tr>
                    <#list errors as error>
                    <tr>
                        <td>${error.code}</td>
                        <td>${error.message}</td>
                        <td>${error.deal}</td>
                    </tr>
                    </#list>
                </table>
            </div>
            <!-- ./业务错误码 -->

        </div>
        <!-- content -->
    </div>
<script src="/doc/js/jquery.js"></script>
<script src="/doc/js/api.js"></script>
</body>

</html>