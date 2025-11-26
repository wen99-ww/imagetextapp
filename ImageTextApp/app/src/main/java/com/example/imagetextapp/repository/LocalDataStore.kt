package com.example.imagetextapp.repository

import com.example.imagetextapp.model.Author
import com.example.imagetextapp.model.Clip
import com.example.imagetextapp.model.Post

object LocalDataSource {
    fun getMockPosts(): List<Post> {
        println("LocalDataSource: Generating mock posts")
        return listOf(
            Post(
                post_id = "1",
                title = "美丽的风景",
                content = "今天去了一个很漂亮的地方 #旅行 #风景",
                create_time = System.currentTimeMillis() / 1000 - 86400,
                author = Author(
                    user_id = "user1",
                    nickname = "旅行爱好者",
                    avatar = "https://picsum.photos/100/100?random=1"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 400,
                        url = "https://picsum.photos/300/400?random=1"
                    )
                ),
                like_count = 123,
                is_liked = false
            ),
            Post(
                post_id = "2",
                title = "美食分享",
                content = "尝试了新的餐厅，味道很棒！ #美食 #探店",
                create_time = System.currentTimeMillis() / 1000 - 172800,
                author = Author(
                    user_id = "user2",
                    nickname = "美食家",
                    avatar = "https://picsum.photos/100/100?random=2"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 500,
                        url = "https://picsum.photos/300/500?random=2"
                    )
                ),
                like_count = 456,
                is_liked = false
            ),
            Post(
                post_id = "3",
                title = "编程学习",
                content = "今天学习了新的Android开发技术 #编程 #学习",
                create_time = System.currentTimeMillis() / 1000 - 259200,
                author = Author(
                    user_id = "user3",
                    nickname = "开发者",
                    avatar = "https://picsum.photos/100/100?random=3"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 450,
                        url = "https://picsum.photos/300/450?random=3"
                    )
                ),
                like_count = 789,
                is_liked = false
            ),
            // 添加更多测试数据...
            Post(
                post_id = "4",
                title = "运动健身",
                content = "晨跑5公里，感觉很棒！ #运动 #健康",
                create_time = System.currentTimeMillis() / 1000 - 345600,
                author = Author(
                    user_id = "user4",
                    nickname = "运动达人",
                    avatar = "https://picsum.photos/100/100?random=4"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 420,
                        url = "https://picsum.photos/300/420?random=4"
                    )
                ),
                like_count = 234,
                is_liked = false
            ),
            Post(
                post_id = "5",
                title = "读书笔记",
                content = "读完了一本很好的书，收获很多 #阅读 #学习",
                create_time = System.currentTimeMillis() / 1000 - 432000,
                author = Author(
                    user_id = "user5",
                    nickname = "书虫",
                    avatar = "https://picsum.photos/100/100?random=5"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 380,
                        url = "https://picsum.photos/300/380?random=5"
                    )
                ),
                like_count = 567,
                is_liked = false
            ),
            Post(
                post_id = "6",
                title = "音乐分享",
                content = "推荐一首好听的歌曲 #音乐 #分享",
                create_time = System.currentTimeMillis() / 1000 - 518400,
                author = Author(
                    user_id = "user6",
                    nickname = "音乐迷",
                    avatar = "https://picsum.photos/100/100?random=6"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 410,
                        url = "https://picsum.photos/300/410?random=6"
                    )
                ),
                like_count = 890,
                is_liked = false
            ),
            Post(
                post_id = "7",
                title = "旅行日记",
                content = "今天去了美丽的西湖，风景如画 #旅行 #杭州",
                create_time = System.currentTimeMillis() / 1000 - 432000, // 5天前
                author = Author(
                    user_id = "user7",
                    nickname = "旅行家",
                    avatar = "https://picsum.photos/100/100?random=7"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 400,
                        height = 300,
                        url = "https://picsum.photos/400/300?random=7"
                    )
                ),
                like_count = 234,
                is_liked = true
            ),

            Post(
                post_id = "8",
                title = "美食制作",
                content = "自制意大利面，超级好吃 #美食 #烹饪",
                create_time = System.currentTimeMillis() / 1000 - 345600, // 4天前
                author = Author(
                    user_id = "user8",
                    nickname = "美食达人",
                    avatar = "https://picsum.photos/100/100?random=8"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 400,
                        height = 500,
                        url = "https://picsum.photos/400/500?random=8"
                    )
                ),
                like_count = 567,
                is_liked = false
            ),

            Post(
                post_id = "9",
                title = "健身打卡",
                content = "第30天健身记录，坚持就是胜利 #健身 #自律",
                create_time = System.currentTimeMillis() / 1000 - 259200, // 3天前
                author = Author(
                    user_id = "user9",
                    nickname = "健身狂人",
                    avatar = "https://picsum.photos/100/100?random=9"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 400,
                        url = "https://picsum.photos/300/400?random=9"
                    )
                ),
                like_count = 789,
                is_liked = true
            ),

            Post(
                post_id = "10",
                title = "读书分享",
                content = "推荐一本好书《人类简史》 #读书 #学习",
                create_time = System.currentTimeMillis() / 1000 - 172800, // 2天前
                author = Author(
                    user_id = "user10",
                    nickname = "书虫",
                    avatar = "https://picsum.photos/100/100?random=10"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 450,
                        url = "https://picsum.photos/300/450?random=10"
                    )
                ),
                like_count = 123,
                is_liked = false
            ),

            Post(
                post_id = "11",
                title = "摄影作品",
                content = "清晨的日出，美得让人窒息 #摄影 #自然",
                create_time = System.currentTimeMillis() / 1000 - 86400, // 1天前
                author = Author(
                    user_id = "user11",
                    nickname = "摄影师",
                    avatar = "https://picsum.photos/100/100?random=11"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 500,
                        height = 300,
                        url = "https://picsum.photos/500/300?random=11"
                    )
                ),
                like_count = 456,
                is_liked = true
            ),

            Post(
                post_id = "12",
                title = "编程学习",
                content = "今天学习了Kotlin协程，很有收获 #编程 #Android",
                create_time = System.currentTimeMillis() / 1000 - 43200, // 12小时前
                author = Author(
                    user_id = "user12",
                    nickname = "程序员",
                    avatar = "https://picsum.photos/100/100?random=12"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 400,
                        height = 300,
                        url = "https://picsum.photos/400/300?random=12"
                    )
                ),
                like_count = 321,
                is_liked = false
            ),

            Post(
                post_id = "13",
                title = "宠物日常",
                content = "我家猫咪今天特别可爱 #宠物 #萌宠",
                create_time = System.currentTimeMillis() / 1000 - 21600, // 6小时前
                author = Author(
                    user_id = "user13",
                    nickname = "猫奴",
                    avatar = "https://picsum.photos/100/100?random=13"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 400,
                        height = 400,
                        url = "https://picsum.photos/400/400?random=13"
                    )
                ),
                like_count = 654,
                is_liked = true
            ),

            Post(
                post_id = "14",
                title = "电影推荐",
                content = "刚看完《星际穿越》，强烈推荐 #电影 #科幻",
                create_time = System.currentTimeMillis() / 1000 - 10800, // 3小时前
                author = Author(
                    user_id = "user14",
                    nickname = "影迷",
                    avatar = "https://picsum.photos/100/100?random=14"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 350,
                        height = 500,
                        url = "https://picsum.photos/350/500?random=14"
                    )
                ),
                like_count = 987,
                is_liked = false
            ),

            Post(
                post_id = "15",
                title = "购物分享",
                content = "新买的衣服到货了，质量超好 #购物 #时尚",
                create_time = System.currentTimeMillis() / 1000 - 7200, // 2小时前
                author = Author(
                    user_id = "user15",
                    nickname = "购物达人",
                    avatar = "https://picsum.photos/100/100?random=15"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 300,
                        height = 400,
                        url = "https://picsum.photos/300/400?random=15"
                    )
                ),
                like_count = 234,
                is_liked = true
            ),

            Post(
                post_id = "16",
                title = "运动记录",
                content = "今天跑了5公里，感觉很棒 #运动 #跑步",
                create_time = System.currentTimeMillis() / 1000 - 3600, // 1小时前
                author = Author(
                    user_id = "user16",
                    nickname = "跑步者",
                    avatar = "https://picsum.photos/100/100?random=16"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 400,
                        height = 300,
                        url = "https://picsum.photos/400/300?random=16"
                    )
                ),
                like_count = 567,
                is_liked = false
            ),

            Post(
                post_id = "17",
                title = "工作日常",
                content = "今天完成了重要项目，很有成就感 #工作 #成长",
                create_time = System.currentTimeMillis() / 1000 - 1800, // 30分钟前
                author = Author(
                    user_id = "user17",
                    nickname = "职场人",
                    avatar = "https://picsum.photos/100/100?random=17"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 450,
                        height = 300,
                        url = "https://picsum.photos/450/300?random=17"
                    )
                ),
                like_count = 890,
                is_liked = true
            ),

            Post(
                post_id = "18",
                title = "周末放松",
                content = "周末在家喝茶看书，很惬意 #生活 #放松",
                create_time = System.currentTimeMillis() / 1000 - 600, // 10分钟前
                author = Author(
                    user_id = "user18",
                    nickname = "生活家",
                    avatar = "https://picsum.photos/100/100?random=18"
                ),
                clips = listOf(
                    Clip(
                        type = 0,
                        width = 400,
                        height = 300,
                        url = "https://picsum.photos/400/300?random=18"
                    )
                ),
                like_count = 123,
                is_liked = false
            )

        ).also {
            println("LocalDataSource: Generated ${it.size} mock posts")
        }
    }
}