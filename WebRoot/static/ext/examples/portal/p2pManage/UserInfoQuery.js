// 用户详细信息查询

Ext.define('App.UserInfoQueryWindow', {
	extend : 'Ext.window.Window',
	constructor : function(config) {
		
		config = config || {};
		Ext.apply(config, {
			title : '用户详细信息',
			width : 350,
			height : 450,
			bodyPadding : '10 5',
			layout : 'fit',
			modal : true,
			items : [ {
				xtype : 'form',
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 90,
					anchor : '100%'
				},
				items : [ {
						name : "cmd",
						xtype : "hidden",
						value : 'new'
					},{
					xtype : 'textfield',
					name : 'userId',
					fieldLabel : '用户ID'
					
				},{
						xtype : 'hiddenfield',
						name : 'accountId'
					},{
						xtype : 'hiddenfield',
						name : 'authzId'
					},{
						xtype : 'hiddenfield',
						name : 'contactId'
					}, {
					xtype : 'textfield',
					name : 'phone',
					fieldLabel : '用户电话'
				}, {
					xtype : 'textfield',
					name : 'password',
					fieldLabel : '登录密码'
				},{
					xtype : 'textfield',
					name : 'email',
					fieldLabel : '用户邮箱'
				},{
					xtype : 'textfield',
					name : 'balance',
					fieldLabel : 'balance'
				},{
					xtype : 'textfield',
					name : 'frozen',
					fieldLabel : 'frozen'
				},{
					xtype : 'textfield',
					name : 'contactName',
					fieldLabel : '紧急联系人'
				},{
					xtype : 'textfield',
					name : 'contactPhone',
					fieldLabel : 'contactPhone'
				},{
					xtype : 'textfield',
					name : 'realName',
					fieldLabel : 'realName'
				},{
					xtype : 'textfield',
					name : 'idCardNO',
					fieldLabel : 'idCardNO'
				}],
					buttons : [ '->', {
						id : 'userinfoquerywindow-save',
						text : '保存',
						iconCls : 'icon-save',
						width : 80,
							handler : function(btn, eventObj) {					
						var dt = new Date();
						var window = btn.up('window');
						var form = window.down('form').getForm();
						//alert(Ext.getCmp('test').getValue);
						if (form.isValid()) {
							window.getEl().mask('数据保存中，请稍候...');
							var vals = form.getValues();
								//alert(appBaseUri + '/sys/borrow/saveBorrow');
								Ext.Ajax.request({
								url : appBaseUri + '/sys/userInfo/saveUserInfo',
								params : {
									cmd : vals['cmd'],
									userId : vals['userId'],
									accountId:vals['accountId'],
									contactId:vals['contactId'],
									phone:vals['phone'],
									email:vals['email'],
									password:vals['password'],
//									createDate:vals['createDate'],
									authzId:vals['authzId']
								
								},
								method : "POST",
								success : function(response) {
									if (response.responseText != '') {
										var res = Ext.JSON.decode(response.responseText);
										if (res.success) {
											globalObject.msgTip('操作成功！');
											Ext.getCmp('userinfoquerygrid').getStore().reload();
										} else {
											globalObject.errTip(res.message);
										}
									}
								},
								failure : function(response) {
									globalObject.errTip('操作失败！');
								}
							});
								window.getEl().unmask();
								window.close();
							}
						}
					}, {
						id : 'userinfoquerywindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'userinfoquerywindow-accept',
						text : '确定',
						hidden : true,
						iconCls : 'icon-accept',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, '->' ]
				} ]
			});
			App.UserInfoQueryWindow.superclass.constructor.call(this, config);
		}
	});
	

	
Ext.define('p2p.app.p2pManage.UserInfoQuery', {
	extend : 'Ext.grid.Panel',
	region : 'center',
	initComponent : function() {
		var me = this;

		Ext.define('ModelList', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [ {
				name : 'id',
				type : 'long'
			}, 'userId','phone','email',{
				name : 'contactId',
				type : 'int'				
			},{
				name : 'authzId',
				type : 'int'				
			},'contactName','contactPhone','realName','idCardNO','password',{
				name : 'accountId',
				type : 'int'				
			}, {
				name : 'balance',
				type : 'float'				
			},{
				name : 'frozen',
				type : 'float'				
			}, {
				name : 'createDate',
				type : 'datetime',
				dateFormat : 'Y-m-d H:i:s'

			}]
		});

		var repaymentDateStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/p2p/getRepaymentDate',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'ItemValue'
				}
			},
			fields : [ 'ItemText', 'ItemValue' ]
		});
		// 重要---------------------
		var userInfoQuerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/userInfo/getUserInfo',
				extraParams : me.extraParams || null,
				reader : {
					type : 'json',
					root : 'data',
					totalProperty : 'totalRecord',
					successProperty : "success"
				}
			},
			sorters : [ {
				property : 'id',
				direction : 'DESC'
			} ]
		});

		var columns = [ {
			text : "用户ID",
			dataIndex : 'userId',
			width : '6%'
		}, {
			text : "用户phone",
			dataIndex : 'phone',
			width : '12%'
			
		}, {
			text : "用户email",
			dataIndex : 'email',
			width : '12%'
				
			
		},{
			text : "用户密码",
			dataIndex : 'password',
			width : '12%',
			sortable : false,
			hidden:true
			
		},{
			text : "注册日期",
			dataIndex : 'createDate',
			width : '13%'			
		},{
			text : "账户ID",
			dataIndex : 'accountId',
			width : '6%'
		},{
			text : "可用余额",
			dataIndex : 'balance',
			width : '7%'
		},{
			text : "冻结金额",
			dataIndex : 'frozen',
			width : '7%'
		},{
			text : "联系人ID",
			dataIndex : 'contactId',
			width : '6%'
		},{
			text : "紧急联系人",
			dataIndex : 'contactName',
			width : '7%'
		},{
			text : "联系人手机",
			dataIndex : 'contactPhone',
			width : '14%'
		},{
			text : "真实姓名",
			dataIndex : 'realName',
			width : '8%'
		},{
			text : "身份证号",
			dataIndex : 'idCardNO',
			width : '17%'
		},{
			xtype : 'actioncolumn',
			width : '8%',			
			items : [ {
				iconCls : 'icon-view',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
				tooltip : '查看',
				handler : function(grid, rowIndex, colIndex) {
					var gridRecord = grid.getStore().getAt(rowIndex);
					var win = new App.UserInfoQueryWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					
						Ext.getCmp('userinfoquerywindow-save').hide();
						Ext.getCmp('userinfoquerywindow-cancel').hide();
						Ext.getCmp('userinfoquerywindow-accept').show();
						win.show();
					}
			} ,{
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.UserInfoQueryWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						//form.findField("userId").setReadOnly(true);
											
						win.show();
					}
				}]}];


		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'userInfoQuery-userId',
				name : 'userId',
				fieldLabel : '用户ID',
				labelWidth : 60,
				width : '10%'
			},{
				xtype : 'textfield',
				fieldLabel : '账户ID',
				id : 'userInfoQuery-account',
				name : 'accountId',
				editable : true,
				labelWidth : 60,
				width : '35%',
				maxWidth : 120
			},{
				xtype : 'textfield',
				fieldLabel : '用户phone',
				id : 'userInfoQuery-phone',
				name : 'phone',			
				editable : true,
				labelWidth : 80,
				width : '35%',
				maxWidth : 220
			},{
			    xtype : 'textfield',
			    name : 'email',
			    id : 'userInfoQuery-email',	
				fieldLabel : '邮箱',
				//vtype : 'email',
				labelWidth : 40,
				maxLength : 30
			}, {
				xtype : 'datefield',
				id : 'userInfoQuery-createDate',				
				name : 'createDate',
				fieldLabel : '注册日期',
				labelWidth : 70,
				width : '19%',
				format : 'Y-m-d'
			},  {
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Query'),
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					
					var searchParams = {
							userId : Ext.getCmp('userInfoQuery-userId').getValue(),
							phone : Ext.getCmp('userInfoQuery-phone').getValue(),
							email : Ext.getCmp('userInfoQuery-email').getValue(),
							createDate : Ext.getCmp('userInfoQuery-createDate').getValue(),
							accountId : Ext.getCmp('userInfoQuery-account').getValue()
// repaymentStateId : Ext.getCmp('userInfoQuery-repaymentDate').getValue()
					};
					Ext.apply(userInfoQuerystore.proxy.extraParams, searchParams);
					userInfoQuerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('userInfoQuery-userId').setValue(null);
					Ext.getCmp('userInfoQuery-phone').setValue(null);
					Ext.getCmp('userInfoQuery-createDate').setValue(null);
					Ext.getCmp('userInfoQuery-account').setValue(null);
					Ext.getCmp('userInfoQuery-email').setValue(null);
// Ext.getCmp('userInfoQuery-repaymentDate').setValue(null);
				}
			} ]
		});

		Ext.apply(this, {
			id : 'userinfoquerygrid',
			store : userInfoQuerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : userInfoQuerystore,
				displayInfo : true
			})
		/*
		 * listeners : { itemdblclick : function(dataview, record, item, index,
		 * e) { var grid = this; var id =
		 * grid.getSelectionModel().getSelection()[0].get('id'); var phone =
		 * grid.getSelectionModel().getSelection()[0].get('phone'); var phone =
		 * grid.getSelectionModel().getSelection()[0].get('phone');
		 * alert(phone); var gridRecord = grid.getStore().findRecord('id', id);
		 * var win = new App.UserInfoQueryWindow({ hidden : true }); var form =
		 * win.down('form').getForm(); form.loadRecord(gridRecord);
		 * form.findField('userId').setReadOnly(true);
		 * //form.findField('account').setReadOnly(true);
		 * form.findField('phone').setReadOnly(true);
		 * form.findField('plantTime').setReadOnly(true);
		 * form.findField('entryTime').setReadOnly(true);
		 * //form.findField('repaymentDate').setReadOnly(true); //win.show(); w =
		 * new Ext.window.Window({ width:500, height:400,closeAction:'hide',
		 * constrain:true, layout:'fit', modal:true, html:'<iframe
		 * style=width:490px;height:400px src="../p2p/getRepaymentDate"></iframe>',
		 * title:"标题" // autoScroll:true }); w.show(); } }
		 */
		});
	
		this.callParent(arguments);
	}
});