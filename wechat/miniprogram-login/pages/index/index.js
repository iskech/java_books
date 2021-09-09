//获取应用实例
const app = getApp()

Page({
    getPhoneNumber(e) {
        wx.login({
            success: function (res) {
              // console.log(res.code);
              // console.log(e.detail.iv);
              // console.log(e.detail.encryptedData);
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
                    success: function (result) {
                      console.log(result);
                      wx.request({
                        url: 'http://10.32.55.127:15512/wet/logout',
                        data:{
                         token: result.data.token
                        },
                        header:{
                          "Content-Type": "application/json;charset=UTF-8",
                          "X-phone":result.data.mobile
                        },
                        method:'POST'
                      })
                        //var data = result.data.result;
                       // wx.setStorageSync("userInfo", data);
                    }
                })
            }
        })
        //发起网络请求

    }
})
