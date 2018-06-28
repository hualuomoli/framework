# 配置文件描述

# 使用
使用nginx代理
location /doc/ {
  alias  XXXX/doc/;
  index  index.html index.htm;
}
location /doc/fonts/ {
  alias  XXXX/doc/fonts/;
}
location /doc/css/ {
  alias  XXXX/doc/css/;
}
location /doc/js/ {
  alias  XXXX/doc/js/;
}