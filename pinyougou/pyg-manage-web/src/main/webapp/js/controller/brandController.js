app.controller("brandController",function($scope,$controller,brandService){

    //继承
    $controller("baseController",{$scope:$scope});

    //分页查询
    $scope.findPage = function(page,rows){
        brandService.findPage(page,rows).success(function(response){
            //更新列表数据
            $scope.list = response.rows;

            //更新总记录数
            $scope.paginationConf.totalItems = response.total;
        });
    };

    //保存
    $scope.save = function(){

        var obj;
        //判断是否有id值,如果有则是更新操作
        if($scope.entity.id != null){
            obj = brandService.update($scope.entity);
        }else{
            obj = brandService.add($scope.entity);
        }

        obj.success(function(response){
            if(response.success){
                //保存成功，刷新列表
                $scope.reloadList();
            } else {
                //提示
                alert(response.message);
            }
        });

    };

    //根据id查询品牌对象
    $scope.findOne = function(id){

        brandService.findOne(id).success(function(response){

            $scope.entity = response;
        });

    };


    //删除操作
    $scope.delete = function(){

        //判断是否有选择
        if($scope.selectedIds.length < 1){
            alert("请选择品牌后再删除！");
            return;
        }

        //确认是否删除
        if(confirm("确定删除吗？")){
            brandService.delete($scope.selectedIds).success(function(response){
                if(response.success){
                    //删除成功，刷新列表
                    $scope.reloadList();
                }else{
                    //删除失败
                    alert(response.message);
                }
            });
        }
    };

    //定义一个空的搜索对象
    $scope.searchEntity = {};
    //搜索
    $scope.search = function(page,rows){

        brandService.search(page,rows,$scope.searchEntity).success(function(response){
            //更新列表数据
            $scope.list = response.rows;

            //更新总记录数
            $scope.paginationConf.totalItems = response.total;
        });
    }


});