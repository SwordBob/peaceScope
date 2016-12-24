// 其他资料信息查询
Ext.define('App.OtherInfoQueryWindow', {
	extend : 'Ext.window.Window',
	constructor : function(config) {
		
		config = config || {};
		Ext.apply(config, {
			constrain:true,
			title : '借款项目信息',
			width : 350,
			height : 560,
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
						xtype : 'hiddenfield',
						name : 'otherInfoId'
					},{
						xtype : 'hiddenfield',
						name : 'otherInfoUrl'
					},{
						xtype : 'textfield',
						fieldLabel : '借款ID',
						name : 'borrowId'					
					},{
						xtype : 'textfield',
						fieldLabel : '借款标题',
						name : 'title'					
					},{
						xtype : 'textfield',
						fieldLabel : '借款金额',
						name : 'amount'					
					},{
						xtype : 'textfield',
						fieldLabel : '年利率',
						name : 'interest'					
					},{
						xtype : 'textfield',
						fieldLabel : '项目期限',
						name : 'stages'					
					},{
						xtype : 'textfield',
						fieldLabel : '标种',
						name : 'bidType'					
					},{
						xtype : 'textfield',
						fieldLabel : '还款方式',
						name : 'type'					
					},{
						xtype : 'textfield',
						fieldLabel : '借款日期',
						name : 'borrowDate'					
					},{
						xtype : 'textfield',
						fieldLabel : '借款状态',
						name : 'state'					
					},{
						xtype : 'textfield',
						fieldLabel : '借款人姓名',
						name : 'bidName'					
					},{
						xtype : 'textfield',
						fieldLabel : '借款人籍贯',
						name : 'address'					
					},{
						xtype : 'textfield',
						fieldLabel : '资料名称',
						name : 'otherInfoName'					
					}, {
						xtype : 'combobox',
						name : 'auditState',
						fieldLabel : '审核状态',
						store : [ [ '复审中', '复审中' ], [ '复审通过', '复审通过' ] ],
						valueField : 'StateValue',
						displayField : 'StateText',
						typeAhead : true,
						queryMode : 'local',
						emptyText : '请选择...',
						allowBlank : false,
						editable : false
					}, {
						xtype : 'textfield',
						fieldLabel : '审核人',
						name : 'auditor'
						
					}],
					buttons : [ '->', {
						id : 'otherinfoentrywindow-save',
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
								url : appBaseUri + '/sys/otherInfo/saveOtherInfo',
								params : {
									cmd : vals['cmd'],
									auditState : vals['auditState'],
									otherInfoName:vals['otherInfoName'],
									otherInfoId:vals['otherInfoId'],
									auditor:vals['auditor'],
									otherInfoUrl:vals['otherInfoUrl'],
									borrowId:vals['borrowId']
								},
								method : "POST",
								success : function(response) {
									if (response.responseText != '') {
										var res = Ext.JSON.decode(response.responseText);
										if (res.success) {
											globalObject.msgTip('操作成功！');
											Ext.getCmp('otherinfoquerygrid').getStore().reload();
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
						id : 'otherinfoentrywindow-cancel',
						text : '取消',
						iconCls : 'icon-cancel',
						width : 80,
						handler : function() {
							this.up('window').close();
						}
					}, {
						id : 'otherinfoentrywindow-accept',
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
			App.OtherInfoQueryWindow.superclass.constructor.call(this, config);
		}
	});
	
Ext.define('p2p.app.p2pManage.OtherInfoQuery', {
	extend : 'Ext.grid.Panel',
	region : 'center',
	initComponent : function() {
		var me = this;
		/*var auditStateStore = new Ext.data.SimpleStore({
			fields : [ 'StateValue', 'StateText' ],
			data : [ [ false, '否' ], [ true, '是' ] ]
		});*/
		
	

		Ext.define('ModelList', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [ {
				name : 'otherInfoId',
				type : 'int'
			},'otherInfoName', 'userId', 'carInfoId',{
				name : 'borrowId',
				type : 'int'
			},'title' ,{
				name : 'amount',
				type : 'int'
			},{
					name : 'borrowDate',
					type : 'datetime',
					dateFormat : 'Y-m-d H:i:s'					
				},{
				name : 'interest',
				type : 'float'
			},{
				name : 'stages',
				type : 'int'
			},'bidType', 'type', 'borrowDate', 'state', 
			'bidName','address','auditState', 'auditor','otherInfoUrl']
		});
		
		var otherInfoQuerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/otherInfo/getOtherInfo',
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
			text : "借款ID",
			dataIndex : 'borrowId',
			width : '6%'
			
		},{
			text : "借款标题",
			dataIndex : 'title',
			width : '10%'
			
		},{
			text : "还款方式",
			dataIndex : 'type',
			width : '10%'
		
		},{
			text : "标种",
			dataIndex : 'bidType',
			width : '10%'
			
		},{
				text : "借款日期",
				dataIndex : 'borrowDate',
				width : '14%'
			},{
			text : "资料ID",
			dataIndex : 'otherInfoId',
			width : '10%'
		}, {
			text : "资料名称",
			dataIndex : 'otherInfoName',
			width : '20%'
			
		}, {
			text : "审核状态",
			dataIndex : 'auditState',
			width : '8%'
			
		},{
			text : "审核人",
			dataIndex : 'auditor',
			width : '8%'
		}, {
			text : "图片路径",
			dataIndex : 'otherInfoUrl',
			hidden : true,
			sortable : false,
			width : '8%'
			},{
			xtype : 'actioncolumn',
			width : '18%',			
			items : [ {
					iconCls : 'icon-pictures',
					tooltip : '资料',
					handler : function(grid, rowIndex, colIndex) {
						var entity = grid.getStore().getAt(rowIndex);
						var test='http://localhost:8080/images/'+entity.get('otherInfoUrl');
						new Ext.window.Window({	
						  
							width : 750,
							height : 550,
							bodyPadding : '10 5',
							modal : true,
							autoScroll : true,
							items : [ {
								html:window.open(test,"111","width=600,height=600,top=100,left=300,scrollbars=1") 
							} ]
						});
					}
				}, {
				iconCls : 'icon-view',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'View'),
				tooltip : '查看',
				handler : function(grid, rowIndex, colIndex) {
					var gridRecord = grid.getStore().getAt(rowIndex);
					var win = new App.OtherInfoQueryWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('otherInfoId').setReadOnly(true);
						form.findField('otherInfoName').setReadOnly(true);
						form.findField('auditState').setReadOnly(true);
						form.findField('auditor').setReadOnly(true);
						
						Ext.getCmp('otherinfoentrywindow-cancel').hide();
						Ext.getCmp('otherinfoentrywindow-accept').show();
						win.show();
					}
			} ,{
					iconCls : 'edit',
					tooltip : '修改',
					disabled : !globalObject.haveActionMenu(me.cButtons, 'Edit'),
					handler : function(grid, rowIndex, colIndex) {
						var gridRecord = grid.getStore().getAt(rowIndex);
						var win = new App.OtherInfoQueryWindow({
							hidden : true
						});
						var form = win.down('form').getForm();
						form.loadRecord(gridRecord);
						form.findField("cmd").setValue("edit");
						form.findField('otherInfoId').setReadOnly(true);
						form.findField('otherInfoName').setReadOnly(true);
					//	form.findField('auditState').setReadOnly(true);
						form.findField('borrowId').setReadOnly(true);
						form.findField('auditor').setReadOnly(true);
											
						win.show();
					}
				}]}];

		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'otherInfoQuery-borrowId',
				name : 'borrowId',
				fieldLabel : '借款ID',
				labelWidth : 60,
				width : '10%'
			},{
				xtype : 'textfield',
				id : 'otherInfoQuery-otherInfoId',
				name : 'otherInfoId',
				fieldLabel : '资料ID',
				labelWidth : 60,
				width : '10%'
			},{
				xtype : 'textfield',
				fieldLabel : '资料名称',
				id : 'otherInfoQuery-otherInfoName',
				name : 'otherInfoName',				
				labelWidth : 60,
				width : '35%',
				maxWidth : 220
			}, {
				xtype : 'textfield',
				id : 'otherInfoQuery-auditor',				
				name : 'auditor',
				fieldLabel : '审核人',
				labelWidth : 50,
				width : '13%'
			}, {
				xtype : 'combobox',
				id : 'otherInfoQuery-auditState',
				name : 'auditState',
				fieldLabel : '审核状态',
				store: [['复审中','复审中'],['复审通过','复审通过']],
				valueField : 'StateValue',
				displayField : 'StateText',
				typeAhead : true,
				queryMode : 'local',
				emptyText : '请选择...',
				allowBlank : false,
				editable : true,
				labelWidth : 60,
				width : '35%',
				maxWidth : 180
				
			},/*{
				xtype : 'combobox',
				fieldLabel : '还款日期',
				id : 'otherInfoQuery-repaymentDate',
				name : 'repaymentDate',
				store : repaymentDateStore,
				valueField : 'ItemValue',
				displayField : 'ItemText',
				typeAhead : true,
				queryMode : 'remote',
				emptyText : '请选择...',
				editable : false,
				labelWidth : 90,
				width : '30%',
				maxWidth : 220
			}, */{
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Query'),
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					var searchParams = {
						otherInfoId : Ext.getCmp('otherInfoQuery-otherInfoId').getValue(),
						otherInfoName : Ext.getCmp('otherInfoQuery-otherInfoName').getValue(),
						auditor : Ext.getCmp('otherInfoQuery-auditor').getValue(),
						auditState : Ext.getCmp('otherInfoQuery-auditState').getValue(),
						borrowId : Ext.getCmp('otherInfoQuery-borrowId').getValue()
					};
					Ext.apply(otherInfoQuerystore.proxy.extraParams, searchParams);
					otherInfoQuerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('otherInfoQuery-otherInfoId').setValue(null);
					Ext.getCmp('otherInfoQuery-otherInfoName').setValue(null);
					Ext.getCmp('otherInfoQuery-auditor').setValue(null);
					Ext.getCmp('otherInfoQuery-auditState').setValue(null);
					Ext.getCmp('otherInfoQuery-borrowId').setValue(null);
				}
			} ]
		});

		Ext.apply(this, {
			id : 'otherinfoquerygrid',
			store : otherInfoQuerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : otherInfoQuerystore,
				displayInfo : true
			})
		/*	listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					var grid = this;
					var id = grid.getSelectionModel().getSelection()[0].get('id');
					var phone = grid.getSelectionModel().getSelection()[0].get('phone');
					var phone = grid.getSelectionModel().getSelection()[0].get('phone');
					alert(phone);
					var gridRecord = grid.getStore().findRecord('id', id);
					var win = new App.BorrowQuery({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('userId').setReadOnly(true);
					//form.findField('account').setReadOnly(true);
					form.findField('phone').setReadOnly(true);
					form.findField('plantTime').setReadOnly(true);
					form.findField('entryTime').setReadOnly(true);
					//form.findField('repaymentDate').setReadOnly(true);
					//win.show();
					 w = new Ext.window.Window({					      
					       width:500,				      
					       height:400,closeAction:'hide',
					       constrain:true,
					       layout:'fit',
					       modal:true,
					       html:'<iframe style=width:490px;height:400px src="../p2p/getRepaymentDate"></iframe>',
					       title:"标题" 
					      // autoScroll:true
					    });						
					w.show();	
				}
			}*/
		});
	
	
		this.callParent(arguments);
	}
});