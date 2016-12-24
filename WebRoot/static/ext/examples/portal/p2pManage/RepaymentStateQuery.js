// 还款状态信息查询

Ext.define('p2p.app.p2pManage.RepaymentStateQuery', {
	extend : 'Ext.grid.Panel',
	region : 'center',
	initComponent : function() {
		var me = this;
		/*var stateStore = new Ext.data.SimpleStore({
			fields : [ 'StateValue', 'StateText' ],
			data : [ [ false, '否' ], [ true, '是' ] ]
		});*/
		
	

		Ext.define('ModelList', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : [ {
				name : 'id',
				type : 'long'
			}, 'epcId','sysUserName','sysUserId', 'name', {
				name : 'state',
				type : 'string'
			}, {
				name : 'plantTime',
				type : 'datetime',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'entryTime',
				type : 'datetime',
				dateFormat : 'Y-m-d H:i:s',
				convert:function(value){
					//alert(value);
				}
			}, {
				name : 'repaymentDate',
				type : 'datetime',		
				convert:function(value){	
					//alert(value);
				var repaymentDate= Ext.Date.format(new Date(value),"Y-m-d H:i:s");
             	return repaymentDate;  
				}
			},'repaymentStateId']
		});
		var sysUserNameStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/sysuser/getSysUserName',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'UserNameValue'
				}
			},
			fields : [ 'UserNameText', 'UserNameValue' ]
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
		var repaymentStateQuerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/p2p/getp2p',
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
			text : "ID",
			dataIndex : 'id',
			width : '5%'
		}, {
			text : "epc编码",
			dataIndex : 'epcId',
			width : '21%'
		}, {
			text : "用户姓名",
			dataIndex : 'sysUserName',
			width : '16%',
			sortable : false
		}, {
			text : "sysUserId",
			dataIndex : 'sysUserId',
			hidden : true,
			sortable : false
		}, {
			text : "借款名称",
			dataIndex : 'name',
			width : '18%'
		}, {
			text : "是否通过审核",
			dataIndex : 'state',
			width : '8%',
			sortable : false
			/*renderer : function(v) {
				if (v == true) {
					return '是';
				} else {
					return '否';
				}
			}*/
		},{
			text : "借款日期",
			dataIndex : 'plantTime',
			width : '17%'
		}, /*{
			text : "借款期限",
			dataIndex : 'entryTime',
			width : '17%'
		}, */{
			text : "还款日期",			
			dataIndex : 'repaymentDate',			
			width : '17%',
			sortable : false					
		}, {
			text : "repaymentStateId",
			dataIndex : 'repaymentStateId',
			hidden : true,
			sortable : false
		}];

		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'repaymentStateQuery-epcId',
				name : 'epcId',
				fieldLabel : 'epc编码',
				labelWidth : 60,
				width : '15%'
			},{
				xtype : 'textfield',
				fieldLabel : '用户',
				id : 'repaymentStateQuery-sysUserName',
				name : 'sysUserName',
				store : sysUserNameStore,
				valueField : 'UserNameValue',
				displayField : 'UserNameText',
				typeAhead : true,
				queryMode : 'remote',
				editable : true,
				labelWidth : 30,
				width : '35%',
				maxWidth : 120
			}, {
				xtype : 'textfield',
				id : 'repaymentStateQuery-name',				
				name : 'name',
				fieldLabel : '借款名称',
				labelWidth : 40,
				width : '20%'
			}, {
				xtype : 'combobox',
				id : 'repaymentStateQuery-state',
				name : 'state',
				fieldLabel : '是否通过审核',
				store: [['未审核','未审核'],['待审核','待审核'],['审核通过','审核通过']],
				valueField : 'StateValue',
				displayField : 'StateText',
				typeAhead : true,
				queryMode : 'local',
				emptyText : '请选择...',
				allowBlank : false,
				editable : true,
				width : '35%',
				maxWidth : 180
				
			},/*{
				xtype : 'combobox',
				fieldLabel : '还款日期',
				id : 'repaymentStateQuery-repaymentDate',
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
						epcId : Ext.getCmp('repaymentStateQuery-epcId').getValue(),
						sysUserId : Ext.getCmp('repaymentStateQuery-sysUserName').getValue(),
						name : Ext.getCmp('repaymentStateQuery-name').getValue(),
						state : Ext.getCmp('repaymentStateQuery-state').getValue()
//						repaymentStateId : Ext.getCmp('repaymentStateQuery-repaymentDate').getValue()
					};
					Ext.apply(repaymentStateQuerystore.proxy.extraParams, searchParams);
					repaymentStateQuerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('repaymentStateQuery-epcId').setValue(null);
					Ext.getCmp('repaymentStateQuery-sysUserName').setValue(null);
					Ext.getCmp('repaymentStateQuery-name').setValue(null);
					Ext.getCmp('repaymentStateQuery-state').setValue(null);
//					Ext.getCmp('repaymentStateQuery-repaymentDate').setValue(null);
				}
			} ]
		});

		Ext.apply(this, {
			store : repaymentStateQuerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : repaymentStateQuerystore,
				displayInfo : true
			}),
			listeners : {
				itemdblclick : function(dataview, record, item, index, e) {
					var grid = this;
					var id = grid.getSelectionModel().getSelection()[0].get('id');
					var gridRecord = grid.getStore().findRecord('id', id);
					var win = new App.RepaymentStateQueryWindow({
						hidden : true
					});
					var form = win.down('form').getForm();
					form.loadRecord(gridRecord);
					form.findField('epcId').setReadOnly(true);
					form.findField('sysUserName').setReadOnly(true);
					form.findField('name').setReadOnly(true);
					form.findField('plantTime').setReadOnly(true);
					form.findField('entryTime').setReadOnly(true);
					form.findField('repaymentDate').setReadOnly(true);
					win.show();
				}
			}
		});
	
		this.callParent(arguments);
	}
});