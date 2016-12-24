// 还款状态信息查询

Ext.define('p2p.app.p2pManage.ReceivePlanQuery', {
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
				name : 'receivePlanId',
				type : 'int'
			},{
				name : 'borrowId',
				type : 'int'
			},{
				name : 'planEstimate',
				type : 'float'
			},{
				name : 'planPenalty',
				type : 'float'
			},'receiveState',{
				name : 'planDate',
				type : 'datetime',
				dateFormat : 'Y-m-d H:i:s'
			}, {
				name : 'planDateEnd',
				type : 'datetime',
				dateFormat : 'Y-m-d H:i:s'
				
			},{
				name : 'partStages',
				type : 'int'
			}]
		});
		
		/*var repaymentDateStore = Ext.create('Ext.data.JsonStore', {
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/receivePlan/getRepaymentDate',
				reader : {
					type : 'json',
					root : 'list',
					idProperty : 'ItemValue'
				}
			},
			fields : [ 'ItemText', 'ItemValue' ]
		});*/
		var receivePlanQuerystore = Ext.create('Ext.data.Store', {
			model : 'ModelList',
			// autoDestroy: true,
			autoLoad : true,
			remoteSort : true,
			pageSize : globalPageSize,
			proxy : {
				type : 'ajax',
				url : appBaseUri + '/sys/receivePlan/getReceivePlan',
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
			width : '7%'
		}, {
			text : "还款计划ID",
			dataIndex : 'receivePlanId',
			width : '7%'
		}, {
			text : "还款状态",
			dataIndex : 'receiveState',
			width : '8%'		
		}, {
			text : "应还金额",
			dataIndex : 'planEstimate',
			width : '13%'
		}, {
			text : "所属期数",
			dataIndex : 'partStages',
			width : '8%'						
		},{
			text : "还款日期",
			dataIndex : 'planDate',
			width : '17%'
		},{
			text : "逾期罚款",
			dataIndex : 'planPenalty',
			width : '13%'
		},{
			text : "还款截止日期",			
			dataIndex : 'planDateEnd',			
			width : '17%'							
		}];

		var ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
			items : [ {
				xtype : 'textfield',
				id : 'receivePlanQuery-borrowId',
				name : 'borrowId',
				fieldLabel : '借款ID',
				labelWidth : 55,
				width : '15%'
			},{
				xtype : 'textfield',
				id : 'receivePlanQuery-receivePlanId',
				name : 'receivePlanId',
				fieldLabel : '还款计划ID',
				labelWidth : 60,
				width : '15%'
			},{
				xtype : 'combobox',
				id : 'receivePlanQuery-receiveState',
				name : 'state',
				fieldLabel : '还款状态',
				store: [['已还款','已还款'],['未还款','未还款']],
				valueField : 'StateValue',
				displayField : 'StateText',
				typeAhead : true,
				queryMode : 'local',
				emptyText : '请选择...',				
				editable : true,
				width : '35%',
				labelWidth : 60,
				maxWidth : 180
				
			},{
				xtype : 'datefield',
				id : 'receivePlanQuery-planDate',				
				name : 'repaymentEnd',
				fieldLabel : '还款日期',
				labelWidth : 60,
				width : '19%',
				format : 'Y-m-d'
			},{
				xtype : 'button',
				text : '查询',
				iconCls : 'icon-search',
				disabled : !globalObject.haveActionMenu(me.cButtons, 'Query'),
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					var searchParams = {
						receivePlanId : Ext.getCmp('receivePlanQuery-receivePlanId').getValue(),
						
						planDate : Ext.getCmp('receivePlanQuery-planDate').getValue(),
						receiveState : Ext.getCmp('receivePlanQuery-receiveState').getValue(),
						borrowId : Ext.getCmp('receivePlanQuery-borrowId').getValue()
					};
					Ext.apply(receivePlanQuerystore.proxy.extraParams, searchParams);
					receivePlanQuerystore.reload();
				}
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'icon-reset',
				width : '10%',
				maxWidth : 60,
				handler : function(btn, eventObj) {
					Ext.getCmp('receivePlanQuery-receivePlanId').setValue(null);					
					Ext.getCmp('receivePlanQuery-planDate').setValue(null);
					Ext.getCmp('receivePlanQuery-receiveState').setValue(null);
					Ext.getCmp('receivePlanQuery-borrowId').setValue(null);
		
				}
			} ]
		});

		Ext.apply(this, {
			store : receivePlanQuerystore,
			columns : columns,
			tbar : ttoolbar,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : receivePlanQuerystore,
				displayInfo : true
			})
			
		});
	
		this.callParent(arguments);
	}
});