简介：Spark在线实时处理引擎的Java客户端

更新日期：20150527

更新内容：更新说明文档，主要完善使用方式。因为代码中间使用了zeromq作为消息中间件，所以需要讲怎么安装和配置zeromq

1.安装zeromq
	
	执行下面命令：
	
	git clone https://github.com/zeromq/zeromq2-x.git

	cd zeromq2-x
	
	./autogen.sh
	
	./configure
	
	make
	
	make install

2.安装jzmq
	
	git clone https://github.com/zeromq/jzmq.git
	
	cd jzmq
	
	./autogen.sh
	
	./configure
	
	make
	
	make install

3.将jzmq加入到java.library.path
	
	vim ~/.bashrc
	
	export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib/
