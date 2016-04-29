## API  <Host: api.zhuishushenqi.com>

#榜单 < /ranking>

	*列表
		http://api.zhuishushenqi.com/ranking/gender
	*内容
		http://api.zhuishushenqi.com/ranking/_id

#分类 < /cats/lv2>

	*所有分类及子类(筛选功能)
		http://api.zhuishushenqi.com/cats/lv2
	*外围类内容(包含图书总数)
		http://api.zhuishushenqi.com/cats/lv2/statistics
	*分类的Tab (从0开始，每页不等数目数据)
		热门：http://api.zhuishushenqi.com/book/by-categories?gender="male/female"&type=hot&major="大类"&minor="小类&start=0&limit=50
		新书：http://api.zhuishushenqi.com/book/by-categories?gender="male/female"&type=new&major="大类"&minor="小类&start=0&limit=50
		好评：http://api.zhuishushenqi.com/book/by-categories?gender="male/female"&type=reputation&major="大类"&minor="小类&start=0&limit=50
		完结：http://api.zhuishushenqi.com/book/by-categories?gender="male/female"&type=over&major="大类"&minor="小类&start=0&limit=50


#搜索 < /book>

	*关键字搜索
		http://api.zhuishushenqi.com/book/auto-complete?query="keywords"
	*图书搜索 最大获得100条数据，无加载更多
		http://api.zhuishushenqi.com/book/fuzzy-search?query="bookname"
	*小说简介
		http://api.zhuishushenqi.com/book/_id

#阅读
	*小说源
		http://api.zhuishushenqi.com/toc?view=summary&book=_id
	*根据源获取章节
		http://api.zhuishushenqi.com/toc/源_id?view=chapters
	*小说正文
		http://chapter2.zhuishushenqi.com/chapter/(URL编码章节)link
	*更新小说
		http://api.zhuishushenqi.com/book?view=updated&id=_id,_id



#数据库表

###搜索历史表
	*	字段		类型
		name	string
		time	string

###我的书架表
	*	字段					备注									类型
		_id					图书id								string
		sourceId			图书源id								string
		title				图书名称								string
		cover				图片地址								string
		author				图书作者								string
		updated				更新时间								string
		lastChapter			最后一章								string
		readCount			已读章节数							int
		readPage			当前章节已读页数						int
		chaptersCount		总章节数								int
		openedTime			最后一次打开的时间(排序标准)			string