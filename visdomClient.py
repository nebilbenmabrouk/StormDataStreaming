from __future__ import print_function

import argparse
#from visdom import Visdom

# General parameters
DEFAULT_VISDOM_HOST = "127.0.0.1"
DEFAULT_VISDOM_PORT = 8099
DEFAULT_DATA_VALUE = 0.00

parser = argparse.ArgumentParser(description='visdom client')

parser.add_argument('--visdom_host', type=str, default=DEFAULT_VISDOM_HOST, 
                    help='IP of the visdom server')
parser.add_argument('--visdom_port', type=int, default=DEFAULT_VISDOM_PORT, 
                    help='IP port of the visdom server')
parser.add_argument('--value', type=float, default=DEFAULT_DATA_VALUE, 
                    help='Y value for the line plot')
args = parser.parse_args()

print("Connecting to visdom server on ",args.visdom_host,":",args.visdom_port)

print(args.value)


iteration = 0

#viz = Visdom(server="http://"+args.visdom_host, port=args.visdom_port)
#assert viz.check_connection()
#win_epoch_loss = viz.line(Y = np.array([1]), X = np.array([1]),
#                          opts = dict(
#                                  xlabel = 'Iteration',
#                                  ylabel = 'Exchange rate',
#                                  title = 'Bitcoin to Chinese Yuan',
#                                  ),
#                          )

#if iteration == 0:
#  viz.line(Y = np.array([average_loss_train]), X = np.array([iteration_train]), win = win_epoch_loss, update='replace')
#else:
#  viz.line(Y = np.array([average_loss_train]), X = np.array([iteration_train]), win = win_epoch_loss, update='append')



