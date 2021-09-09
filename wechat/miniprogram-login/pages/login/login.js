//获取应用实例
const app = getApp();
console.log(app);
Page({
    //定义绑定数据
    data: {
        hasUserInfo: false,
        token: 0,
        mobile: 0,
        loginResult: [],
        object: {
            text: 'init data'
        }
    },
    //获取授权电话函数
    getPhoneNumber(e) {
        var that = this;
        wx.login({
            success: (res) => {
                //发起网络请求
                wx.request({
                    url: "http://10.32.55.127:15512/wet/login",
                    data: {
                        code: res.code,
                        encryptedData: e.detail.encryptedData,
                        iv: e.detail.iv
                    },
                    header: {
                        "Content-Type": "application/json;charset=UTF-8"
                    },
                    method: 'POST',
                    //服务端的回掉
                    success: (result) => {
                        that.loginResult = result.data;
                        that.token = result.data.data.token;
                        that.mobile = result.data.data.mobile;
                        that.setData({ hasUserInfo : 'ture' });
                        console.log("微信小程序登录成功");
                    }
                })
            }
        })


    },
    //退出函数
    logout(e) {
        var that = this;
        wx.request({
            url: 'http://10.32.55.127:15512/wet/logout',
            data: {
                token: that.token
            },
            header: {
                "Content-Type": "application/json;charset=UTF-8",
                "X-phone": that.mobile
            },
            method: 'POST',
            success: (outRes) => {
                wx.clearStorage();
                console.log("微信小程序退出登录成功");
                that.setData({hasUserInfo: false });
            }
        })
    }
})
