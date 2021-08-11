<template>
  <div>
    <el-table
      :data="tableData"
      highlight-current-row
      border
      style="width: 100%">
      <el-table-column
        label="用户名">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="账号">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="密码">
        <template slot-scope="scope">
          <span>{{ scope.row.password }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        fixed="right"
        width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button
      class="el-icon-circle-plus-outline"
      type="text"
      @click="dialogVisible = true">添加账户
    </el-button>
    <el-button
      class="el-icon-circle-plus-outline"
      type="text"
      @click="saveData()">保存信息
    </el-button>

    <el-form :model="ruleForm" ref="ruleForm" label-width="70px" class="demo-ruleForm" size="medium">
      <el-dialog
        title="添加账户"
        :append-to-body='true'
        :visible.sync="dialogVisible"
        width="80%"
        :before-close="handleClose">
        <el-input type="hidden" v-model="ruleForm.id"/>
        <el-form-item label="用户名" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="账号" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="ruleForm.password"></el-input>
        </el-form-item>

        <span slot="footer" class="dialog-footer">
            <el-button @click="cancel()" size="medium">取 消</el-button>
            <el-button @click="addUser()" type="primary" size="medium">确 定</el-button>
          </span>
      </el-dialog>
    </el-form>

    <br>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        name: '',
        username: '',
        password: ''
      },
      tableData: [],
      dialogVisible: false
    }
  },
  methods: {
    handleDelete(index, row) {
      console.log(index, row);
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios({
          method: 'delete',
          url: '/' + row.name
        }).then(response => {
          let searchUrl = this.getSearchUrl()
          let message=response.data;
          this.axios.get(searchUrl).then(response => {
            this.tableData = response.data;
          }).catch(error => {
            console.log(error);
          });
          this.$message({
            type: 'info',
            message: message
          });
          console.log(response);
        }).catch(error => {
          console.log(error);
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    getSearchUrl() {
      return "/list"
    },
    cancel() {
      this.dialogVisible = false;
      this.emptyUserData();
    },
    emptyUserData() {
      this.ruleForm = {
        name: '',
        username: '',
        password: ''
      }
    },
    addUser() {
      let postData = {
        name: this.ruleForm.name,
        username: this.ruleForm.username,
        password: this.ruleForm.password
      };
      this.axios({
        headers: {
          'Content-Type': 'application/json'
        },
        method: 'post',
        url: '/',
        data: JSON.stringify(postData)
      }).then(response => {
        let searchUrl = this.getSearchUrl()
        let message=response.data;
        this.axios.get(searchUrl).then(response => {
          this.tableData = response.data;
          this.$message({
            type: 'info',
            message: message
          });
        }).catch(error => {
          console.log(error);
        });
        this.dialogVisible = false
        console.log(response);
      }).catch(error => {
        console.log(error);
      });
    },
    saveData() {
      this.axios({
        method: 'get',
        url: "/saveData"
      }).then(response => {
        this.$message({
          type: 'info',
          message: response.data
        });
      }).catch(error => {
        console.log(error);
      });
    },
    onSearch() {
      let searchUrl = this.getSearchUrl()
      this.axios({
        method: 'get',
        url: searchUrl
      }).then(response => {
        this.tableData = response.data;
      }).catch(error => {
        console.log(error);
      });
    },
    refreshData() {
      location.reload();
    }
  },
  created() {
    let searchUrl = this.getSearchUrl()
    this.axios.get(searchUrl).then(response => {
      console.log(response)
      debugger
      this.tableData = response.data;
    }).catch(error => {
      console.log(error);
    });

  },
}
</script>
<style scoped>
.search-value {
  width: 200px;
}

</style>
