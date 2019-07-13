package net;

import gen.cmd.Command;
import common.Trace;
import common.marshal.MarshalException;
import common.marshal.Octets;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xio.Coder;
import xio.Protocol;
import xio.Xio;

import java.util.List;
import java.util.Map;

public final class CabCoder extends Coder {

    private static final Logger log = LoggerFactory.getLogger(CabCoder.class);

    private final Map<Integer, Protocol> stubs;

    public CabCoder(Xio session, Xio.Conf conf) {
        super(session, conf);
        this.stubs = conf.stubs;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        if (msg instanceof Octets) {
            ((Octets)msg).writeTo(out, false);
        } else {
            try {
                Octets os = new Octets();
                Command.marshal((Command)msg, os);
                os.writeTo(out, false);
            } catch (Exception ex) {
                log.error("Coder.encode error.", ex);
            }

        }
    }
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        inputBuff.readFrom(in);
        
        while (inputBuff.nonEmpty()) {
            int mark = inputBuff.readerIndex();
            
            final byte tag;            
            try {
                tag = inputBuff.readByte();
                if (tag != (byte)0xEB) {
                    //ctx.close();
                    inputBuff.clear();
                    log.error("session[{}] decode tag:{}, not 0xEB", session.getSid(), tag);
                    return;
                }
            } catch (MarshalException e) {
                inputBuff.rollbackReadIndex(mark);
                log.debug("session[{}] read head not enough.", session.getSid());
                break;
            } catch (Exception e) {
                e.printStackTrace();
                //ctx.close();
                inputBuff.clear();
                return;
            }            
            
            //Trace.info("-> size={}, inputBuff={}", inputBuff.size(),            		inputBuff.toHexString(true));

            Trace.info("size={}, inputBuff={}", inputBuff.size(), inputBuff.toHexString(true));
            
            try {
                byte cabId = inputBuff.readByte();
                short cmd = inputBuff.readFixShort();
                short size = inputBuff.readFixShort();

                //Trace.info("--> size={}", inputBuff.size());
                
                if (inputBuff.size() < size) {
                	throw new MarshalException("23");
                }
                
                oneMessageBuff.wrapRead(inputBuff, size);
                int msgHeadIndex = oneMessageBuff.readerIndex();
                final Octets os = oneMessageBuff;
                
                Protocol msg = this.stubs.get((int)cmd);
                if (msg == null) {
                    this.oneMessageBuff.rollbackReadIndex(msgHeadIndex);
                    if (!this.session.getManager().onUnknownMessage(this.session, cmd, os)) {
                        log.debug("session[{}] onUnknownMessage type:{} size:{}", new Object[]{this.session.getSid(), cmd, size});
                        ctx.close();
                        inputBuff.clear();
                        return;
                    }
                } else {
                    msg = msg.newObject();
                    msg.unmarshal(os);
                    ((Command)msg).cabId = cabId;
                    out.add(msg);
                }
            } catch (MarshalException e) {
            	e.printStackTrace();
                inputBuff.rollbackReadIndex(mark);
                log.debug("session[{}] read head not enough.", session.getSid());
                break;
            } catch (Exception e) {
                e.printStackTrace();
                ctx.close();
                inputBuff.clear();
                return;
            }
        }

        if (inputBuff.empty()) {
            inputBuff.clear();
        }
    }
}
