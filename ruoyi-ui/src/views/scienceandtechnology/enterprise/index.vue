<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="企业名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入企业名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="企业法人" prop="juridicalPerson">
        <el-input v-model="queryParams.juridicalPerson" placeholder="请输入企业法人" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="联系人" label-width="100px" prop="linkman">
        <el-input v-model="queryParams.linkman" placeholder="请输入企业法人电话" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="资产总额区间(万元)" label-width="140px" prop="totalAssetsRange">
        <el-select v-model="queryParams.totalAssetsRange" style="width:70%" placeholder="请选择资产总额区间" clearable>
          <el-option value="">
            请选择
          </el-option>
          <el-option v-for="option in dict.type.total_assets_range" :key="option.value" :label="option.label"
            :value="option.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="产业分类" prop="industryOneLevel">
        <el-select v-model="queryParams.industryOneLevel" placeholder="请选择产业分类" clearable>
          <el-option value="">
            请选择
          </el-option>
          <el-option v-for="dict in dict.type.applicable_industries" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="注册时间">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['scienceandtechnology:enterprise:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['scienceandtechnology:enterprise:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['scienceandtechnology:enterprise:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport"
          v-hasPermi="['scienceandtechnology:enterprise:import']">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['scienceandtechnology:enterprise:export']">导出</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="enterpriseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="企业名称" width="120" align="center" prop="name" />
      <el-table-column label="注册企业地址" width="100" align="center" prop="address" />
      <el-table-column label="企业法人" align="center" prop="juridicalPerson" />
      <el-table-column label="企业法人电话" width="110" align="center" prop="juridicalPersonPhone" />
      <el-table-column label="注册时间" align="center" prop="registrationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.registrationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="社会统一信用代码号" width="140" align="center" prop="socialUnifiedCreditCodeNumber" />
      <el-table-column label="企业性质或控股类型" width="140" align="center" prop="enterpriseNature">
        <template slot-scope="scope">
          <span v-for="item in dict.type.enterprise_nature" v-text="item.label"
            v-if="item.value == scope.row.enterpriseNature"></span>
        </template>

      </el-table-column>
      <el-table-column label="营业期限" width="100" align="center" prop="businessTerm">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.businessTerm, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="资产总额区间" width="100" align="center" prop="totalAssetsRange">
        <template slot-scope="scope">
          <span v-if="scope.row.totalAssetsRange == 1">0至50万</span>
          <span v-if="scope.row.totalAssetsRange == 2">50至200万</span>
          <span v-if="scope.row.totalAssetsRange == 3">200至500万</span>
          <span v-if="scope.row.totalAssetsRange == 4">500至1000万</span>
          <span v-if="scope.row.totalAssetsRange == 5">1000万至1亿</span>
          <span v-if="scope.row.totalAssetsRange == 6">1亿以上</span>
        </template>
      </el-table-column>
      <el-table-column label="注册资本(万元)" align="center" width="120" prop="registeredCapital" />
      <el-table-column label="实缴资本(万元)" align="center" width="120" prop="seeAlso" />
      <el-table-column label="官方网站" align="center" prop="officialWebsite" />
      <el-table-column label="企业邮箱" width="120" align="center" prop="enterpriseMailbox" />
      <el-table-column label="在职人数" align="center" prop="numberOfEmployees" />
      <el-table-column label="营业执照" align="center" prop="businessLicenseUrl">
        <template slot-scope="scope">
          <image-preview :src="scope.row.businessLicenseUrl" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['enterprise:enterprise:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['enterprise:enterprise:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改企业对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="企业名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入企业名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在职人数" prop="numberOfEmployees">
              <el-input-number v-model="form.numberOfEmployees" :min='1' placeholder="请输入在职人数"></el-input-number>
            </el-form-item>
          </el-col>



        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="企业法人" prop="juridicalPerson">
              <el-input v-model="form.juridicalPerson" placeholder="请输入企业法人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业法人电话" prop="juridicalPersonPhone">
              <el-input v-model="form.juridicalPersonPhone" placeholder="请输入企业法人电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人" prop="linkman">
              <el-input v-model="form.linkman" placeholder="请输入企业联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人电话" prop="linkmanPhone">
              <el-input v-model="form.linkmanPhone" placeholder="请输入企业联系人电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>




          <el-form-item label="注册归属地" prop="registrationStreet">
            <el-select v-model="form.registrationProvince" placeholder="请选择省" v-on="{ focus: seeProvince }"
              @change="seeCity(2)" style="width:25%">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in ProvinceList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="form.registrationCity" placeholder="请选择市" @change="seeDistinct(2)" style="width:25%">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in CityList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="form.registrationRegion" placeholder="请选择区" style="width:25%" @change="seeStreet(2)">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in DistinctList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="form.registrationStreet" placeholder="请选择街道" style="width:25%" @change="seeAny">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in streetList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>

        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="产业分类" prop="industryOneLevel">
              <el-select v-model="form.industryOneLevel" placeholder="请选择产业分类" clearable>
                <el-option value="">
                  请选择
                </el-option>
                <el-option v-for="dict in dict.type.applicable_industries" :key="dict.value" :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="所属行业" prop="industryTwoLevel">
              <el-select v-model="form.industryTwoLevel" placeholder="请选择所属行业" clearable>
                <el-option value="">
                  请选择
                </el-option>
                <el-option v-for="dict in dict.type.trade" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="企业性质或控股类型" prop="enterpriseNature">
              <el-select v-model="form.enterpriseNature" placeholder="请选择企业性质">
                <el-option value="">
                  请选择
                </el-option>
                <el-option v-for="item in dict.type.enterprise_nature" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>


          </el-col>


          <el-col :span="12">
            <el-form-item label="资产总额区间" prop="totalAssetsRange">
              <el-select v-model="form.totalAssetsRange" placeholder="请选择资产总额区间">
                <el-option value="">
                  请选择
                </el-option>
                <el-option v-for="item in dict.type.total_assets_range" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>

          <el-col :span="12">

            <el-form-item label="实缴资本(万元)" prop="seeAlso">
              <el-input v-model="form.seeAlso" placeholder="请输入实缴资本(万元)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="注册资本(万元)" prop="registeredCapital">
              <el-input v-model="form.registeredCapital" placeholder="请输入注册资本(万元)" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>


          <el-form-item label="官方网站(没有写无)" prop="officialWebsite">
            <el-input v-model="form.officialWebsite" placeholder="请输入官方网站(没有写无)" />
          </el-form-item>

        </el-row>
        <el-row>
          <el-form-item label="企业邮箱" prop="enterpriseMailbox">
            <el-input v-model="form.enterpriseMailbox" placeholder="请输入企业邮箱" />
          </el-form-item>
        </el-row>

        <el-row>

          <el-form-item label="社会统一信用代码号" prop="socialUnifiedCreditCodeNumber">
            <el-input v-model="form.socialUnifiedCreditCodeNumber" placeholder="请输入社会统一信用代码号" />
          </el-form-item>

        </el-row>

        <el-row>

          <el-form-item label="经营地址" prop="manageAddress">
            <el-input v-model="form.manageAddress" placeholder="请输入经营地址" />
          </el-form-item>

        </el-row>
        <el-row>
          <el-form-item label="注册企业地址" prop="address">
            <el-input v-model="form.address" placeholder="请输入注册企业地址" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="登记机关" prop="registrationAuthority">
            <el-input v-model="form.registrationAuthority" placeholder="请输入登记机关" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="经营范围" prop="natureOfBusiness">
            <el-input v-model="form.natureOfBusiness" placeholder="请简述企业经营范围" type="textarea" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="主营业务" prop="mainBusiness">
            <el-input v-model="form.mainBusiness" placeholder="请简述主营业务" type="textarea" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="符合政策信息" prop="complianceWithPolicyInformation">
            <el-input v-model="form.complianceWithPolicyInformation" placeholder="请简述符合政策信息" type="textarea" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="营业期限" prop="businessTerm" required>
              <el-date-picker clearable v-model="form.businessTerm" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择注册时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="注册时间" prop="registrationTime">
              <el-date-picker clearable v-model="form.registrationTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择注册时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>

          <el-form-item label="企业标签" prop="tagIds">
            <el-select v-model="form.tagIds" placeholder="请选择企业标签" multiple style="width: -webkit-fill-available;">
              <el-option value="">
                请选择
              </el-option>
              <el-option v-for="item in tags" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="营业执照" prop="businessLicenseUrl" required>
              <image-upload v-model="form.businessLicenseUrl" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登记日期" prop="registrationDate" required>
              <el-date-picker clearable v-model="form.registrationDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择登记日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 企业导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的企业数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
            @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEnterprise, getEnterprise, delEnterprise, addEnterprise, updateEnterprise, getCity, getTags } from "@/api/scienceandtechnology/enterprise/enterprise";
import { getToken } from "@/utils/auth";
export default {
  name: "Enterprise",
  dicts: ['enterprise_nature', 'total_assets_range', 'applicable_industries', 'trade'],
  data() {
    return {
      //全局地址
      ctx: 'scienceandtechnology/enterprise',
      // 企业导入参数
      upload: {
        // 是否显示弹出层（企业导入）
        open: false,
        // 弹出层标题（企业导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "scienceandtechnology/enterprise/importData"
      },
      //初始化标签
      tags: [],
      CityList: null,
      ProvinceList: null,
      DistinctList: null,
      streetList: null,
      enterpriseNatureList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 企业表格数据
      enterpriseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        address: null,
        juridicalPerson: null,
        juridicalPersonPhone: null,
        enterpriseNature: null,
        numberOfEmployees: null,
        businessLicenseUrl: null,
        distinct: null,
        linkman: null,
        industryOneLevel: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        registrationStreet: [
          { required: true, message: "企业注册归属地不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        juridicalPerson: [
          { required: true, message: "企业法人不能为空", trigger: "blur" }
        ],
        linkman: [
          { required: true, message: "企业联系人不能为空", trigger: "blur" }
        ],
        tagIds: [
          { required: true, message: "企业标签不能为空", trigger: "blur" }
        ],
        socialUnifiedCreditCodeNumber: [
          { required: true, message: "请确保社会统一信用代码号正确", trigger: "blur" }
        ],
        enterpriseMailbox: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        juridicalPersonPhone: [
          {
            required: true,
            message: "企业法人手机号码不能为空", trigger: "blur",
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        linkmanPhone: [
          {
            required: true,
            message: "企业联系人手机号码不能为空", trigger: "blur",
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getTagList();
  },
  methods: {
    /** 查询企业列表 */
    getList() {
      this.loading = true;
      listEnterprise(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.enterpriseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getTagList() {
      getTags().then(response => {
        this.tags = response
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        address: null,
        juridicalPerson: null,
        juridicalPersonPhone: null,
        registrationTime: null,
        registrationProvince: null,
        registrationCity: null,
        registrationRegion: null,
        manageAddress: null,
        socialUnifiedCreditCodeNumber: null,
        enterpriseNature: null,
        registrationType: null,
        totalAssetsRange: null,
        registeredCapital: null,
        seeAlso: null,
        officialWebsite: null,
        enterpriseMailbox: null,
        industryOneLevel: null,
        industryTwoLevel: null,
        numberOfEmployees: null,
        businessLicenseUrl: null,
        createTime: null,
        createBy: null,
        tagIds: null,
        businessTerm: null,
        natureOfBusiness: null,
        registrationAuthority: null,
        registrationStreet: null,
        mainBusiness: null,
        complianceWithPolicyInformation: null,
        registrationDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加企业";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();

      const id = row.id || this.ids
      const array = [];
      getEnterprise(id).then(response => {
        this.form = response.data;
        if (this.form.registrationProvince != null) {
          this.seeProvince();
        }
        if (this.form.registrationCity != null) {
          this.seeCity(1);
        }
        if (this.form.registrationRegion != null) {
          this.seeDistinct(1);
        }
        if (this.form.registrationStreet != null) {
          this.seeStreet(1);
        }
        this.tags.forEach(element => {
          if (this.form.tagIds != null && this.form.tagIds.indexOf(element.id) != -1) {

            array.push(element.id);
          }
        });

        this.form.tagIds = array;
        this.open = true;
        this.title = "修改企业";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          //处理数组转string
          if (this.form.tagIds.length > 0) {

            this.form.tagIds = this.form.tagIds.join(",");
          }
          if (this.form.id != null) {
            
            updateEnterprise(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
           
            addEnterprise(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除企业编号为"' + ids + '"的数据项？').then(function () {
        return delEnterprise(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('scienceandtechnology/enterprise/export', {
        ...this.queryParams
      }, `enterprise_${new Date().getTime()}.xlsx`)
    },
    seeProvince() {
      const ProvinceId = 0;
      getCity(ProvinceId).then(response => {
        this.ProvinceList = response
      });



    },
    seeCity(type) {
      getCity(this.form.registrationProvince).then(response => { this.CityList = response });
      if (type == 2) {
        this.form.registrationCity = null;
        this.CityList = null;
        this.DistinctList = null;
        this.form.registrationRegion = null;
        this.streetList = null;
        this.form.registrationStreet = null;
      }

    },
    seeDistinct(type) {

      getCity(this.form.registrationCity).then(response => {
        this.DistinctList = response
      });
      if (type == 2) {
        this.DistinctList = null;
        this.form.registrationRegion = null;
        this.streetList = null;
        this.form.registrationStreet = null;
      }
    },
    seeStreet(type) {
      getCity(this.form.registrationRegion).then(response => {
        this.streetList = response
      });
      if (type == 2) {
        this.streetList = null;
        this.form.registrationStreet = null;
      }
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(this.ctx + '/importTemplate', {
      }, `企业导入模板${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
